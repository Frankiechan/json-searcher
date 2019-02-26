package com.jsonsearcher

import cats.effect.{ContextShift, IO, Sync}
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import com.jsonsearcher.core._
import com.jsonsearcher.core.index.UserViewIndices
import com.jsonsearcher.models._
import com.jsonsearcher.utils.ResourcesLoader


object Main extends App {

  implicit val contextShift = IO.contextShift(global)
  Runtime[IO]().unsafeRunSync()

}

object Runtime {
  def apply[F[_]]()(implicit F: Sync[F], cs: ContextShift[F]): F[Unit] = {
    val resourcesOrNot = ResourcesLoader.load()
    val userViewOrNot = resourcesOrNot.mapN((users, tickets, orgs) => UserViewGenerator.generate((users, tickets, orgs)))

    val searchResultsOrNot = userViewOrNot.flatMap(u => F.pure(
      {
        val st = IntSearchTerm("_id", 1)
        val searchBoard = SimpleSearchEngine(u, UserViewIndices.preload(u))

        searchBoard.search(st)
      }
    ))
    import io.circe.generic.auto._
    import io.circe.syntax._

    searchResultsOrNot.flatMap(x => F.pure(x.map(_.asJson).foreach(println(_))))
  }
}
