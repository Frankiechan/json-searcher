package com.jsonsearcher.core.index

import com.jsonsearcher.core.IndexDictionaries
import com.jsonsearcher.models.TicketView

object TicketViewIndices {

  def _id(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket._id, TicketViews)

  def url(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.url, TicketViews)

  def external_id(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.external_id, TicketViews)

  def created_at(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.created_at, TicketViews)

  def _type(TicketViews: List[TicketView]) = Indexer.optionIndex((t: TicketView) => t.ticket._type, TicketViews)

  def subject(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.subject, TicketViews)

  def description(TicketViews: List[TicketView]) = Indexer.optionIndex((t: TicketView) => t.ticket.description, TicketViews)

  def priority(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.priority, TicketViews)

  def status(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.status, TicketViews)

  def submitter_id(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.submitter_id, TicketViews)

  def assignee_id(TicketViews: List[TicketView]) = Indexer.optionIndex((t: TicketView) => t.ticket.assignee_id, TicketViews)

  def has_incidents(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.has_incidents, TicketViews)

  def due_at(TicketViews: List[TicketView]) = Indexer.optionIndex((t: TicketView) => t.ticket.due_at, TicketViews)

  def via(TicketViews: List[TicketView]) = Indexer.index((t: TicketView) => t.ticket.via, TicketViews)

  def organization_id(TicketViews: List[TicketView]) = Indexer.optionIndex((t: TicketView) => t.ticket.organization_id, TicketViews)

  def tags(TicketViews: List[TicketView]) = Indexer.arrayIndex((t: TicketView) => t.ticket.tags, TicketViews)

  def preload(t: List[TicketView]): IndexDictionaries = {

    val longIndices = Map(
      "submitter_id" -> submitter_id(t),
      "assignee_id" -> assignee_id(t),
      "organization_id" -> organization_id(t)
    )

    val strIndices = Map(
      "_id" -> _id(t),
      "url" -> url(t),
      "external_id" -> external_id(t),
      "created_at" -> created_at(t),
      "type" -> _type(t),
      "subject" -> subject(t),
      "description" -> description(t),
      "priority" -> priority(t),
      "status" -> status(t),
      "tags" -> tags(t),
      "due_at" -> due_at(t),
      "via" -> via(t),
    )

    val boolIndices = Map(
      "has_incidents" -> has_incidents(t)
    )

    IndexDictionaries(longIndices, strIndices, boolIndices)
  }
}
