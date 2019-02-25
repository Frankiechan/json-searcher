package com.jsonsearcher

import cats.effect.{ContextShift, IO, Sync}
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import com.jsonsearcher.core._
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



        val searchBoard = SearchBoard(u, UserIndices.preload(u))

        for {
          index <- searchBoard.indices.intIndices.get("organization_id")
          positions <- index.get(101)
        } yield positions.map(u.lift(_))

      }
    ))
    import io.circe.generic.auto._
    import io.circe.syntax._

    searchResultsOrNot.flatMap(x => F.pure(x.map(_.asJson).foreach(println(_))))
  }
}
