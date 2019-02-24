package com.jsonsearcher.core

import com.jsonsearcher.Resources
import com.jsonsearcher.models._

trait ViewGenerator[R, V] {
  def generate(r: R): List[V]
}

object UserViewGenerator extends ViewGenerator[Resources, UserView] {
  override def generate(r: (List[User], List[Ticket], List[Organization])): List[UserView] = {
    val (users, tickets, orgs) = r

    val orgsGroupById = orgs.groupBy(_._id).map{ case(id, l) => (id, l.headOption) }
    val ticketsGroupBySubmitterId = tickets.groupBy(_.submitter_id)
    val ticketsGroupByAssigneeId = tickets.groupBy(_.assignee_id)

    users.map(user => UserView(
      user = user,
      org = user.organization_id.flatMap(orgsGroupById.getOrElse(_, None)),
      submittedTickets = ticketsGroupBySubmitterId.getOrElse(user._id, List.empty),
      assignedTickets = ticketsGroupByAssigneeId.getOrElse(Some(user._id), List.empty)
    ))
  }
}

object OrganisationViewGenerator extends ViewGenerator[Resources, OrganizationView] {
  override def generate(r: (List[User], List[Ticket], List[Organization])): List[OrganizationView] = {
    val (users, tickets, orgs) = r
    val usersGroupByOrgId = users.groupBy(_.organization_id)
    val ticketsGroupByOrgId = tickets.groupBy(_.organization_id)

    orgs.map(org => OrganizationView(
      org = org,
      users = usersGroupByOrgId.getOrElse(Some(org._id), List.empty),
      tickets = ticketsGroupByOrgId.getOrElse(Some(org._id), List.empty)
    ))
  }
}

object TicketViewGenerator extends ViewGenerator[Resources, TicketView] {
  override def generate(r: (List[User], List[Ticket], List[Organization])): List[TicketView] = {
    val (users, tickets, orgs) = r
    val orgsGroupById = orgs.groupBy(_._id).map{ case(id, l) => (id, l.headOption) }
    val usersGroupById = users.groupBy(_._id).map{ case(id, l) => (id, l.headOption) }

    tickets.map(ticket => TicketView(
      ticket=ticket,
      org=ticket.organization_id.flatMap(orgsGroupById.getOrElse(_, None)),
      assignee=ticket.assignee_id.flatMap(usersGroupById.getOrElse(_, None)),
      submitter = usersGroupById.getOrElse(ticket.submitter_id, None)
    ))
  }
}


