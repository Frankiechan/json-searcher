package com.jsonsearcher.models

sealed trait View

case class UserView(user: User, org: Option[Organization], assignedTickets: List[Ticket], submittedTickets: List[Ticket]) extends View
case class TicketView(ticket: Ticket, org: Option[Organization], assignee: Option[User], submitter: Option[User]) extends View
case class OrganizationView(org: Organization, users: List[User], tickets: List[Ticket]) extends View
