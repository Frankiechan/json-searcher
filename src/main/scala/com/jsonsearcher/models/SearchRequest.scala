package com.jsonsearcher.models

sealed trait SearchRequest {
  def term: String
  def content: String
}

case class UserSearchRequest(term: String, content: String) extends SearchRequest

case class TicketSearchRequest(term: String, content: String) extends SearchRequest

case class OrgSearchRequest(term: String, content: String) extends SearchRequest

