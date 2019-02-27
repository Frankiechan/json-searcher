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

class SearchStoreInitialiser[F[_], V <: View](private val viewGnerator: Resources => List[V],
                                              private val preloadIndexDictionaries: List[V] => IndexDictionaries,
                                             )(implicit F: Sync[F]) {
  def init(): F[SearchStore] = {
    val resourcesOrNot = ResourcesLoader.load[F]()

    for {
      views <- resourcesOrNot.mapN((users, tickets, orgs) => viewGnerator((users, tickets, orgs)))
      searchStore <- F.pure(SearchStore(views, preloadIndexDictionaries(views)))
    } yield searchStore
  }
}

object SearchStoreInitialiser {
  def userView[F[_]]()(implicit F: Sync[F]): SearchStoreInitialiser[F, UserView] = new SearchStoreInitialiser(UserViewGenerator.generate, UserViewIndices.preload)

  def ticketView[F[_]]()(implicit F: Sync[F]): SearchStoreInitialiser[F, TicketView] = new SearchStoreInitialiser(TicketViewGenerator.generate, TicketViewIndices.preload)

  def orgView[F[_]]()(implicit F: Sync[F]): SearchStoreInitialiser[F, OrganizationView] = new SearchStoreInitialiser(OrganizationViewGenerator.generate, OrgViewIndices.preload)
}