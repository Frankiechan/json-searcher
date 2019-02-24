package com.jsonsearcher.core

import com.jsonsearcher.Resources
import org.scalatest.FunSpec
import com.jsonsearcher.fixtures.ModelData
import com.jsonsearcher.models.{OrganizationView, TicketView, UserView}

class ViewGeneratorSpec extends FunSpec {
  describe("Given a resource containing tickets, users and organisations") {
    val resources: Resources = (List(ModelData.user), List(ModelData.ticket), List(ModelData.org))

    it("should be able to convert the resources to an UserView") {
      val expectedUserView = UserView(
        user = ModelData.user,
        org = Some(ModelData.org),
        assignedTickets = List(ModelData.ticket),
        submittedTickets = List(ModelData.ticket)
      )

      assert(UserViewGenerator.generate(resources) == List(expectedUserView))
    }

    it("should be able to convert the resources to an OrganizationView ") {
      val expectedOrgView = OrganizationView(
        org = ModelData.org,
        users = List(ModelData.user),
        tickets = List(ModelData.ticket)
      )

      assert(OrganizationViewGenerator.generate(resources) == List(expectedOrgView))
    }

    it("should be able to convert the resources to an TicketView ") {
      val expectedTicketView = TicketView(
        ticket = ModelData.ticket,
        org = Some(ModelData.org),
        assignee = Some(ModelData.user),
        submitter = Some(ModelData.user)
      )

      assert(TicketViewGenerator.generate(resources) == List(expectedTicketView))
    }
  }
}
