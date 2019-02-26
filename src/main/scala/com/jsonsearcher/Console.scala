package com.jsonsearcher

import cats.effect.{Console, IO, Sync}
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.jsonsearcher.core.SimpleSearchEngine
import com.jsonsearcher.models.ConsoleInteractions._
import com.jsonsearcher.utils.UserSearchRequestBuilder

object ConsoleMain extends App {

  import cats.effect.Console.implicits._

  val model = new InteractionModel[IO](Start, None, new UserSearchRequestBuilder[IO]())

  while (true) {
    model.interact("").unsafeRunSync()
  }
}

class InteractionModel[F[_]](private var stage: ConsoleInteractionStage,
                             private var searchEngine: Option[SimpleSearchEngine],
                             private var searchRequestBuild: UserSearchRequestBuilder[F])
                            (implicit F: Sync[F], C: Console[F]) {

  import Conversation._
  import io.circe.generic.auto._
  import io.circe.syntax._

  def interact(input: String): F[Unit] = {
    this.stage match {
      case Start =>
        for {
          _ <- C.putStrLn(s"debug ${stage.toString}")
          _ <- C.putStrLn(openning)
          n <- readLnOrQuit
          _ <- if (n == "") update(Instructions) else update(Start)
        } yield ()
      case Instructions =>
        for {
          _ <- C.putStrLn(moreInfo)
          n <- readLnOrQuit
          _ <- n match {
            case "1" => update(StartSearch)
            case "2" => update(ViewAvailableFields)
            case _ => update(Instructions)
          }
        } yield ()

      case StartSearch =>
        for {
          _ <- C.putStrLn(searchInstruction)
          n <- readLnOrQuit
          _ <- n match {
            case "1" =>
              SaerchEngineInitialiser.userView().init().flatMap(s => searchEngineInit(s))
            case "2" => C.putStrLn("WIP load TicketViewSearchEngine")
            case "3" => C.putStrLn("WIP load OrgViewSearchEngine")
          }
          _ <- update(EnterSearchTerm)
        } yield ()
      case ViewAvailableFields =>
        for {
          _ <- C.putStrLn(searchableFields)
          _ <- readLnOrQuit
          _ <- update(Instructions)
        } yield ()
      case EnterSearchTerm =>
        for {
          _ <- C.putStrLn(enterSearchTerm)
          n <- readLnOrQuit
          _ <- F.pure(searchRequestBuild.withTerm(n))
          _ <- C.putStrLn("WIP load search term")
          _ <- update(EnterSearchValue)
        } yield ()
      case EnterSearchValue =>
        for {
          _       <- C.putStrLn(enterSearchValue)
          n       <- readLnOrQuit
          _       <- F.pure(searchRequestBuild.withContent(n))
          results <- searchEngine match {
            case Some(s) => searchRequestBuild.build().map(x => s.search(x.searchTerm))
            case None => F.raiseError(new RuntimeException("No search Engine detext"))
          }
          _ <- F.pure(results.map(x => x.map(_.asJson)).foreach(println(_)))
          _ <- C.putStrLn("WIP load search value and return searchResult")
          _ <- update(SearchCompleted)
        } yield ()
      case SearchCompleted => for {
        _ <- C.putStrLn(searchCompleted)
        _ <- update(EnterSearchTerm)
      } yield ()
    }
  }

  private def update(stage: ConsoleInteractionStage): F[Unit] = F.pure(this.stage = stage)

  private def readLnOrQuit(): F[String] = {
    for {
      n <- C.readLn
      r <- if (n.toLowerCase == "quit") F.pure(sys.exit(0)) else F.pure(n)
    } yield r
  }

  private def searchEngineInit(s: SimpleSearchEngine): F[Unit] = F.pure(this.searchEngine = Some(s))
}

object Conversation {
  val openning = "type quit to exist at anytime, Press 'Enter' to continue"

  val moreInfo =
    """
      |Select Search Options:
      |* Press 1 to search Zendesk
      |* Press 2 to view a list of searchable fields
      |* Type `quit` to exit
      |
    """.stripMargin


  val searchInstruction =
    """
      |1) Users or 2) Tickets or 3) Organizations
    """.stripMargin


  val searchableFields =
    """
      |Here are all the searchable fields;
    """.stripMargin


  val enterSearchTerm = "enter search term"

  val enterSearchValue = "enter search value"

  val searchCompleted = "Search Completed, please enter search key to search again or Enter 'quit' to exit"

  def invalidInput(v: String) = println(s"invalid input ${v}, please enter again")
}