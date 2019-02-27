package com.jsonsearcher

import cats.data.NonEmptyList
import cats.effect.Console.implicits._
import cats.effect.{Console, IO, Sync}
import cats.implicits._
import com.jsonsearcher.core.SearchOperation
import com.jsonsearcher.models._

class ConsoleRun[F[_]](searchOperation: (SearchTerm, SearchStore) => F[NonEmptyList[View]])
                      (implicit F: Sync[F], C: Console[F]) {

  import FastQuit.readLnOrQuit

  def atStart(): F[FiniteConsoleState] = {
    val openning = "type quit to exist at anytime, Press 'Enter' to continue"
    val process: F[FiniteConsoleState] = for {
      _ <- C.putStrLn(openning)
      n <- readLnOrQuit
      next <- if (n == "") F.pure(Instruction) else F.pure(Start)
    } yield next

    HandleError[F](process, Start)
  }

  def atInstruction(): F[FiniteConsoleState] = {
    val moreInfo =
      """
        |Select Search Options:
        |* Press 1 to search Zendesk
        |* Press 2 to view a list of searchable fields
        |* Type `quit` to exit
      """.stripMargin

    val process: F[FiniteConsoleState] = for {
      _ <- C.putStrLn(moreInfo)
      n <- readLnOrQuit
      next <- n match {
        case "1" => F.pure(StartSearch)
        case "2" => F.pure(Start)
        case _ => F.pure(Instruction)
      }
    } yield next

    HandleError[F](process, Instruction)
  }

  def atStartSearch(): F[FiniteConsoleState] = {
    val searchInstruction =
      """
        |1) Users or 2) Tickets or 3) Organizations
      """.stripMargin


    val process: F[FiniteConsoleState] = for {
      _ <- C.putStrLn(searchInstruction)
      n <- readLnOrQuit
      next <- n match {
        case "1" =>
          SearchStoreInitialiser.userView[F]().init().map(EnterSearchTerm(_, UserSearchRequest.apply))
        case "2" =>
          SearchStoreInitialiser.ticketView[F]().init().map(EnterSearchTerm(_, TicketSearchRequest.apply))
        case "3" =>
          SearchStoreInitialiser.orgView[F]().init().map(EnterSearchTerm(_, OrgSearchRequest.apply))
        case _ => F.pure(Instruction)
      }
    } yield next

    HandleError[F](process, Instruction)
  }

  def atEnterSearchTerm(state: EnterSearchTerm): F[FiniteConsoleState] = {
    val enterSearchTerm = "enter search term"

    val process: F[FiniteConsoleState] = for {
      _ <- C.putStrLn(enterSearchTerm)
      term <- readLnOrQuit
      next <-
        F.pure(EnterSearchValue(state.searchStore, state.requestConstructor, term))
    } yield next

    HandleError[F](process, EnterSearchTerm(state.searchStore, state.requestConstructor))
  }

  def atEnterSearchValue(state: EnterSearchValue): F[FiniteConsoleState] = {
    import io.circe.generic.auto._
    import io.circe.syntax._

    val enterSearchValue = "enter search value"

    val process: F[FiniteConsoleState] = for {
      _ <- C.putStrLn(enterSearchValue)
      content <- readLnOrQuit
      request <- F.pure(state.requestConstructor(state.term, content))
      searchTerm <- SearchTermBuilder.build[F](request)
      results <- searchOperation(searchTerm, state.searchStore)
      _ <- F.pure(results.map(_.asJson).map(println(_)))
      _ <- C.putStrLn("Search Completed")
      next <- F.pure(EnterSearchTerm(state.searchStore, state.requestConstructor))
    } yield next

    HandleError[F](process, EnterSearchTerm(state.searchStore, state.requestConstructor))
  }
}

object FastQuit {
  def readLnOrQuit[F[_]]()(implicit F: Sync[F], C: Console[F]): F[String] = {
    for {
      input <- C.readLn
      cleanInput <- F.pure(input.replaceAll("[^a-zA-Z0-9_-]", ""))
      r <- if (cleanInput.toLowerCase == "quit") F.pure(sys.exit(0)) else F.pure(cleanInput)
    } yield r
  }
}

object HandleError {
  def apply[F[_]](stateOrError: F[FiniteConsoleState], back2: FiniteConsoleState)(implicit F: Sync[F]): F[FiniteConsoleState] = {
    stateOrError.attempt.flatMap(v => v match {
      case Right(a) => F.pure(a)
      case Left(e) =>
        ErrorHandler.handle[F](e) >> F.pure(back2)
    })
  }
}

object ConsoleApp {
  def start()= {
    val consoleRun = new ConsoleRun[IO](SearchOperation.apply[IO])
    var stateInIO: IO[FiniteConsoleState] = consoleRun.atStart()

    while (true) {
      stateInIO = stateInIO.handleErrorWith(e => ErrorHandler.handle[IO](e)).unsafeRunSync() match {
        case Start => consoleRun.atStart()
        case Instruction => consoleRun.atInstruction()
        case StartSearch => consoleRun.atStartSearch()
        case s: EnterSearchTerm => consoleRun.atEnterSearchTerm(s)
        case s: EnterSearchValue => consoleRun.atEnterSearchValue(s)
      }
    }
  }
}