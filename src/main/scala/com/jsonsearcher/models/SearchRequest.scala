package com.jsonsearcher.models

sealed trait SearchRequest
case class UserSearchRequest(searchTerm: SearchTerm)
case class TicketSearchRequest(searchTerm: SearchTerm)
case class OrgSearchRquest(searchTerm: SearchTerm)
