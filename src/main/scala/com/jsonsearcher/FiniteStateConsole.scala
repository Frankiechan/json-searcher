package com.jsonsearcher

import cats.effect.{Console, IO, Sync}
import cats.implicits._
import FastQuit.readLnOrQuit
import com.jsonsearcher.core.SimpleSearchEngine
import com.jsonsearcher.models.{SearchRequest, UserSearchRequest}
import com.jsonsearcher.utils.UserSearchRequestBuilder


sealed trait FiniteStateConsole[F[_]] {
  def next(): F[FiniteStateConsole[F]]
}

case class Start[F[_]]()(implicit F: Sync[F], C: Console[F]) extends FiniteStateConsole[F] {
  private val openning = "type quit to exist at anytime, Press 'Enter' to continue"

  override def next(): F[FiniteStateConsole[F]] = {
    for {
      _ <- C.putStrLn(openning)
      n <- readLnOrQuit
      next <- if (n == "") F.pure(Instructions[F]()) else F.pure(Start[F]())
    } yield next
  }
}

case class Instructions[F[_]]()(implicit F: Sync[F], C: Console[F]) extends FiniteStateConsole[F] {
  val moreInfo =
    """
      |Select Search Options:
      |* Press 1 to search Zendesk
      |* Press 2 to view a list of searchable fields
      |* Type `quit` to exit
      |
    """.stripMargin

  override def next(): F[FiniteStateConsole[F]] = {
    for {
      _ <- C.putStrLn(moreInfo)
      n <- readLnOrQuit
      next <- n match {
        case "1" => F.pure(StartSearch[F]())
        case "2" => F.pure(Start[F]())
        case _ => F.pure(Instructions[F]())
      }
    } yield next
  }
}

case class StartSearch[F[_]]()(implicit F: Sync[F], C: Console[F]) extends FiniteStateConsole[F] {
  private val searchInstruction =
    """
      |1) Users or 2) Tickets or 3) Organizations
    """.stripMargin

  override def next(): F[FiniteStateConsole[F]] = {
    for {
      _ <- C.putStrLn(searchInstruction)
      n <- readLnOrQuit
      next <- n match {
        case "1" =>
          F.pure(EnterSearchTerm[F, UserSearchRequest](
            searchEngine = SaerchEngineInitialiser.userView().init(),
            buildSearchRequest = UserSearchRequestBuilder.build[F]))
        case "2" => F.pure(StartSearch[F]())
        case "3" => F.pure(StartSearch[F]())
        case _ => F.pure(StartSearch[F]())
      }
    } yield next
  }
}

case class ViewAvailableFields[F[_]]()(implicit F: Sync[F], C: Console[F]) extends FiniteStateConsole[F] {
  private val searchableFields =
    """
      |Here are all the searchable fields;
    """.stripMargin

  override def next(): F[FiniteStateConsole[F]] = for {
    _ <- C.putStrLn(searchableFields)
    _ <- readLnOrQuit
    next <- F.pure(Instructions[F]())
  } yield next
}

case class EnterSearchTerm[F[_], T <: SearchRequest](searchEngine: F[SimpleSearchEngine],
                                                     buildSearchRequest: String => String => F[T])
                                                    (implicit F: Sync[F], C: Console[F]) extends FiniteStateConsole[F] {
  private val enterSearchTerm = "enter search term"

  override def next(): F[FiniteStateConsole[F]] = {
    for {
      _ <- C.putStrLn(enterSearchTerm)
      n <- readLnOrQuit
      _ <- C.putStrLn("WIP load search term")
      next <-
        F.pure(EnterSearchValue[F, T](term = n,
          searchEngine = searchEngine,
          buildSearchRequest = buildSearchRequest))
    } yield next
  }
}

case class EnterSearchValue[F[_], T <: SearchRequest](term: String,
                                                      searchEngine: F[SimpleSearchEngine],
                                                      buildSearchRequest: String => String => F[T])
                                                     (implicit F: Sync[F], C: Console[F]) extends FiniteStateConsole[F] {

  import io.circe.generic.auto._
  import io.circe.syntax._

  private val enterSearchValue = "enter search value"

  override def next(): F[FiniteStateConsole[F]] = {
    for {
      _ <- C.putStrLn(enterSearchValue)
      n <- readLnOrQuit
      request <- buildSearchRequest(term)(n)
      seacher <- searchEngine
      results <- F.pure(seacher.search(request.searchTerm))
      _ <- F.pure(results.map(x => x.map(_.asJson)).foreach(println(_)))
      _ <- C.putStrLn("WIP load search value and return searchResult")
      next <- F.pure(EnterSearchTerm[F, T](searchEngine = searchEngine, buildSearchRequest = buildSearchRequest))
    } yield next
  }
}


object FastQuit {
  def readLnOrQuit[F[_]]()(implicit F: Sync[F], C: Console[F]): F[String] = {
    for {
      n <- C.readLn
      r <- if (n.toLowerCase == "quit") F.pure(sys.exit(0)) else F.pure(n)
    } yield r
  }
}

object Run extends App {

  import cats.effect.Console.implicits._

  var consoleState = Start[IO]().next()

  while (true) {
    consoleState.map(
      x => consoleState = x.next()
    ).unsafeRunSync()
  }
}
