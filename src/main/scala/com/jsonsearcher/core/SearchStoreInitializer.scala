package com.jsonsearcher.core

import cats.effect.Sync
import cats.implicits._
import com.jsonsearcher.Resources
import com.jsonsearcher.core.index.{OrgViewIndices, TicketViewIndices, UserViewIndices}
import com.jsonsearcher.models._
import com.jsonsearcher.utils.ResourcesLoader

class SearchStoreInitializer[F[_], V <: View](private val viewGnerator: Resources => List[V],
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

object SearchStoreInitializer {
  def userView[F[_]]()(implicit F: Sync[F]): SearchStoreInitializer[F, UserView] =
    new SearchStoreInitializer(UserViewGenerator.generate, UserViewIndices.preload)

  def ticketView[F[_]]()(implicit F: Sync[F]): SearchStoreInitializer[F, TicketView] =
    new SearchStoreInitializer(TicketViewGenerator.generate, TicketViewIndices.preload)

  def orgView[F[_]]()(implicit F: Sync[F]): SearchStoreInitializer[F, OrganizationView] =
    new SearchStoreInitializer(OrganizationViewGenerator.generate, OrgViewIndices.preload)
}
