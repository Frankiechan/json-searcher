package com.jsonsearcher.core.index

import com.jsonsearcher.core.IndexDictionaries
import com.jsonsearcher.models.{TicketView, UserView}

object TicketViewIndices {

//  def _id(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user._id, userViews)
//
//  def url(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.url, userViews)
//
//  def external_id(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.external_id, userViews)
//
//  def name(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.name, userViews)
//
//  def alias(userViews: List[UserView]) =
//    DropNoneIndex.filter(Indexer.index[Option[String], UserView]((u: UserView) => u.user.alias, userViews))
//
//  def created_at(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.created_at, userViews)
//
//  def active(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.active, userViews)
//
//  def verified(userViews: List[UserView]) =
//    DropNoneIndex.filter(Indexer.index[Option[Boolean], UserView]((u: UserView) => u.user.verified, userViews))
//
//  def shared(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.shared, userViews)
//
//  def locale(userViews: List[UserView]) =
//    DropNoneIndex.filter(Indexer.index[Option[String], UserView]((u: UserView) => u.user.locale, userViews))
//
//  def timezone(userViews: List[UserView]) =
//    DropNoneIndex.filter(Indexer.index[Option[String], UserView]((u: UserView) => u.user.timezone, userViews))
//
//  def last_login_at(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.last_login_at, userViews)
//
//  def email(userViews: List[UserView]) =
//    DropNoneIndex.filter(Indexer.index[Option[String], UserView]((u: UserView) => u.user.email, userViews))
//
//  def phone(userViews: List[UserView]) =
//    DropNoneIndex.filter(Indexer.index[Option[String], UserView]((u: UserView) => u.user.phone, userViews))
//
//  def signature(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.signature, userViews)
//
//  def organization_id(userViews: List[UserView]) =
//    DropNoneIndex.filter(Indexer.index[Option[Long], UserView]((u: UserView) => u.user.organization_id, userViews))
//
//  //  def tags(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.tags, userViews)
//  def suspended(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.suspended, userViews)
//
//  def role(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.role, userViews)

  def preload(u: List[TicketView]): IndexDictionaries = {

    IndexDictionaries(Map(), Map(), Map())
  }
}
