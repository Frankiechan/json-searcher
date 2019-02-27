package com.jsonsearcher.core.index

import com.jsonsearcher.core.IndexDictionaries
import com.jsonsearcher.models.UserView

object UserViewIndices {

  def _id(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user._id, userViews)

  def url(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.url, userViews)

  def external_id(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.external_id, userViews)

  def name(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.name, userViews)

  def alias(userViews: List[UserView]) = Indexer.optionIndex((u: UserView) => u.user.alias, userViews)

  def created_at(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.created_at, userViews)

  def active(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.active, userViews)

  def verified(userViews: List[UserView]) = Indexer.optionIndex((u: UserView) => u.user.verified, userViews)

  def shared(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.shared, userViews)

  def locale(userViews: List[UserView]) = Indexer.optionIndex((u: UserView) => u.user.locale, userViews)

  def timezone(userViews: List[UserView]) = Indexer.optionIndex((u: UserView) => u.user.timezone, userViews)

  def last_login_at(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.last_login_at, userViews)

  def email(userViews: List[UserView]) = Indexer.optionIndex((u: UserView) => u.user.email, userViews)

  def phone(userViews: List[UserView]) = Indexer.optionIndex((u: UserView) => u.user.phone, userViews)

  def signature(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.signature, userViews)

  def organization_id(userViews: List[UserView]) = Indexer.optionIndex((u: UserView) => u.user.organization_id, userViews)

  def tags(userViews: List[UserView]) = Indexer.arrayIndex((u: UserView) => u.user.tags, userViews)

  def suspended(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.suspended, userViews)

  def role(userViews: List[UserView]) = Indexer.index((u: UserView) => u.user.role, userViews)

  def preload(u: List[UserView]): IndexDictionaries = {

    val longIndices = Map(
      "_id" -> _id(u),
      "organization_id" -> organization_id(u)
    )

    val strIndices = Map(
      "url" -> url(u),
      "external_id" -> external_id(u),
      "name" -> name(u),
      "alias" -> alias(u),
      "created_at" -> created_at(u),
      "locale" -> locale(u),
      "timezone" -> timezone(u),
      "last_login_at" -> last_login_at(u),
      "email" -> email(u),
      "phone" -> phone(u),
      "signature" -> signature(u),
      "tags" -> tags(u),
      "role" -> role(u)
    )

    val boolIndices = Map(
      "active" -> active(u),
      "verified" -> verified(u),
      "shared" -> shared(u),
      "suspended" -> suspended(u)
    )

    IndexDictionaries(longIndices, strIndices, boolIndices)
  }
}
