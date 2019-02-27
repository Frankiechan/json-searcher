package com.jsonsearcher.core.index

import com.jsonsearcher.core.IndexDictionaries
import com.jsonsearcher.models.OrganizationView

object OrgViewIndices {

  def _id(OrganizationViews: List[OrganizationView]) = Indexer.index((org: OrganizationView) => org.org._id, OrganizationViews)

  def url(OrganizationViews: List[OrganizationView]) = Indexer.index((org: OrganizationView) => org.org.url, OrganizationViews)

  def external_id(OrganizationViews: List[OrganizationView]) = Indexer.index((org: OrganizationView) => org.org.external_id, OrganizationViews)

  def name(OrganizationViews: List[OrganizationView]) = Indexer.index((org: OrganizationView) => org.org.name, OrganizationViews)

  def domain_names(OrganizationViews: List[OrganizationView]) = Indexer.arrayIndex((org: OrganizationView) => org.org.domain_names, OrganizationViews)

  def created_at(OrganizationViews: List[OrganizationView]) = Indexer.index((org: OrganizationView) => org.org.created_at, OrganizationViews)

  def details(OrganizationViews: List[OrganizationView]) = Indexer.index((org: OrganizationView) => org.org.details, OrganizationViews)

  def shared_tickets(OrganizationViews: List[OrganizationView]) = Indexer.index((org: OrganizationView) => org.org.shared_tickets, OrganizationViews)

  def tags(OrganizationViews: List[OrganizationView]) = Indexer.arrayIndex((org: OrganizationView) => org.org.tags, OrganizationViews)

  def preload(org: List[OrganizationView]): IndexDictionaries = {

    val longIndices = Map(
      "_id" -> _id(org),
    )

    val strIndices = Map(
      "url" -> url(org),
      "external_id" -> external_id(org),
      "name" -> name(org),
      "domain_names" -> domain_names(org),
      "created_at" -> created_at(org),
      "details" -> details(org),
      "tags" -> tags(org),
    )

    val boolIndices = Map(
      "shared_tickets" -> shared_tickets(org)
    )

    IndexDictionaries(longIndices, strIndices, boolIndices)
  }
}
