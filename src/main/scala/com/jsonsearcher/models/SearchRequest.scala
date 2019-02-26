package com.jsonsearcher.models

sealed trait SearchRequest {
  def searchTerm: SearchTerm
}

case class UserSearchRequest(searchTerm: SearchTerm) extends SearchRequest

case class TicketSearchRequest(searchTerm: SearchTerm) extends SearchRequest

case class OrgSearchRquest(searchTerm: SearchTerm) extends SearchRequest
