package com.jsonsearcher

import cats.effect.Sync
import cats.implicits._
import com.jsonsearcher.core._
import com.jsonsearcher.core.index.{OrgViewIndices, TicketViewIndices, UserViewIndices}
import com.jsonsearcher.models._
import com.jsonsearcher.utils.ResourcesLoader


object Main extends App {

  ConsoleApp.start()

}

class SearchEngineInitialiser[F[_], V <: View](private val viewGnerator: Resources => List[V],
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

object SearchEngineInitialiser {
  def userView[F[_]]()(implicit F: Sync[F]): SearchEngineInitialiser[F, UserView] = new SearchEngineInitialiser(UserViewGenerator.generate, UserViewIndices.preload)

  def ticketView[F[_]]()(implicit F: Sync[F]): SearchEngineInitialiser[F, TicketView] = new SearchEngineInitialiser(TicketViewGenerator.generate, TicketViewIndices.preload)

  def orgView[F[_]]()(implicit F: Sync[F]): SearchEngineInitialiser[F, OrganizationView] = new SearchEngineInitialiser(OrganizationViewGenerator.generate, OrgViewIndices.preload)
}