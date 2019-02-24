package com.jsonsearcher.core

import com.jsonsearcher.Resources
import com.jsonsearcher.models._

trait ViewGenerator[R, V] {
  def generate(r: R): List[V]
}

object UserViewGenerator extends ViewGenerator[Resources, UserView] {
  override def generate(r: (List[User], List[Ticket], List[Organization])): List[UserView] = {
    val (users, tickets, orgs) = r

    val orgsGroupById = orgs.groupBy(_._id)
    val ticketsGroupBySubmitterId = tickets.groupBy(_.submitter_id)
    val ticketsGroupByAssigneeId = tickets.groupBy(_.assignee_id)

    def orgFromUser(orgIdOrNot: Option[Int]): Option[Organization] = {
      for {
        orgId <- orgIdOrNot
        orgs <- orgsGroupById.get(orgId)
        org <- orgs.headOption
      } yield org
    }

    users.map(user => UserView(
      user = user,
      org = orgFromUser(user.organization_id),
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
      org=org,
      users = usersGroupByOrgId.getOrElse(Some(org._id), List.empty),
      tickets = ticketsGroupByOrgId.getOrElse(Some(org._id), List.empty)
    ))
  }
}

