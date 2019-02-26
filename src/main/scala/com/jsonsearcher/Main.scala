package com.jsonsearcher

import cats.effect.{ContextShift, IO, Sync}
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import com.jsonsearcher.core._
import com.jsonsearcher.core.index.{OrgViewIndices, TicketViewIndices, UserViewIndices}
import com.jsonsearcher.models._
import com.jsonsearcher.utils.ResourcesLoader


object Main extends App {

  implicit val contextShift = IO.contextShift(global)
  Runtime[IO]().unsafeRunSync()

}

object Runtime {
  def apply[F[_]]()(implicit F: Sync[F], cs: ContextShift[F]): F[Unit] = {
    val searchEngineOrNot: F[SimpleSearchEngine] = SaerchEngineInitialiser.userView().init()

    val searchResultsOrNot = searchEngineOrNot.flatMap(s => F.pure({
      val st = IntSearchTerm("_id", 1)
      s.search(st)
    }))

    import io.circe.generic.auto._
    import io.circe.syntax._

    searchResultsOrNot.flatMap(x => F.pure(x.map(_.asJson).foreach(println(_))))
  }
}

class SaerchEngineInitialiser[F[_], V <: View](private val viewGnerator: Resources => List[V],
                                               private val preloadIndices: List[V] => Indices,
                                              )(implicit F: Sync[F]) {
  def init(): F[SimpleSearchEngine] = {
    val resourcesOrNot = ResourcesLoader.load[F]()

    for {
      views <- resourcesOrNot.mapN((users, tickets, orgs) => viewGnerator((users, tickets, orgs)))
      simpleSearchEngine <- F.pure(SimpleSearchEngine(views, preloadIndices(views)))
    } yield simpleSearchEngine
  }
}

object SaerchEngineInitialiser {
  def userView[F[_]]()(implicit F: Sync[F]): SaerchEngineInitialiser[F, UserView] = new SaerchEngineInitialiser(UserViewGenerator.generate, UserViewIndices.preload)

  def ticketView[F[_]]()(implicit F: Sync[F]): SaerchEngineInitialiser[F, TicketView] = new SaerchEngineInitialiser(TicketViewGenerator.generate, TicketViewIndices.preload)

  def orgView[F[_]]()(implicit F: Sync[F]): SaerchEngineInitialiser[F, OrganizationView] = new SaerchEngineInitialiser(OrganizationViewGenerator.generate, OrgViewIndices.preload)
}