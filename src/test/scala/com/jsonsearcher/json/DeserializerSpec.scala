package com.jsonsearcher.json

import com.jsonsearcher.fixtures.JsonString
import com.jsonsearcher.models.{Organization, Ticket, User}
import io.circe.parser
import org.scalatest._


class DeserializerSpec extends FunSpec {

  describe("Given an organization json payload") {
    it("should parse it into a organization object when schema compiled") {
      import com.jsonsearcher.json.Deserializer.decodeOrg
      val decodedOrg = parser.decode[Organization](JsonString.org)
      val expectedResult =
        Organization(
          _id = 101,
          url = "http://initech.zendesk.com/api/v2/organizations/101.json",
          external_id = "9270ed79-35eb-4a38-a46f-35725197ea8d",
          name = "Enthaze",
          domain_names = List("kage.com", "ecratic.com", "endipin.com", "zentix.com"),
          created_at = "2016-05-21T11:10:28 -10:00",
          details = "MegaCorp",
          shared_tickets = false,
          tags = List("Fulton", "West", "Rodriguez", "Farley")
        )

      decodedOrg match {
        case Right(org) => assert(org == expectedResult)
        case Left(error) => fail()
      }
    }
  }

  describe("Given an ticket json payload") {
    it("should parse it into a ticket object when schema compiled") {
      import com.jsonsearcher.json.Deserializer.decodeTicket
      val decodedTicket = parser.decode[Ticket](JsonString.ticket)
      val expectedResult =
        Ticket(
          _id = "50dfc8bc-31de-411e-92bf-a6d6b9dfa490",
          url = "http://initech.zendesk.com/api/v2/tickets/50dfc8bc-31de-411e-92bf-a6d6b9dfa490.json",
          external_id = "8bc8bee7-2d98-4b69-b4a9-4f348ff41fa3",
          created_at = "2016-03-08T09:44:54 -11:00",
          _type = Some("task"),
          subject = "A Problem in South Africa",
          description = Some("Esse nisi occaecat pariatur veniam culpa dolore anim elit aliquip. Cupidatat mollit nulla consectetur ullamco tempor esse."),
          priority = "high",
          status = "hold",
          submitter_id = 43,
          assignee_id = Some(54),
          organization_id = Some(103),
          tags = List("Georgia", "Tennessee", "Mississippi", "Marshall Islands"),
          has_incidents = true,
          due_at = Some("2016-08-03T09:17:37 -10:00"),
          via = "voice"
        )

      decodedTicket match {
        case Right(t) => assert(t == expectedResult)
        case Left(error) => fail()
      }
    }
  }

  describe("Given an User json payload") {
    it("should parse it into an user object when schema compiled") {
      import com.jsonsearcher.json.Deserializer.decodeUser
      val decodedUser = parser.decode[User](JsonString.user)
      val expectedResult = User(_id = 1,
        url = "http://initech.zendesk.com/api/v2/users/1.json",
        external_id = "74341f74-9c79-49d5-9611-87ef9b6eb75f",
        name = "Francisca Rasmussen",
        alias = Some("Miss Coffey"),
        created_at = "2016-04-15T05:19:46 -10:00",
        active = true,
        verified = Some(true),
        shared = false,
        locale = Some("en-AU"),
        timezone = Some("Sri Lanka"),
        last_login_at = "2013-08-04T01:03:27 -10:00",
        email = Some("coffeyrasmussen@flotonic.com"),
        phone = Some("8335-422-718"),
        signature = "Don't Worry Be Happy!",
        organization_id = Some(119),
        tags = List("Springville",
          "Sutton",
          "Hartsville/Hartley",
          "Diaperville"),
        suspended = true,
        role = "admin")

      decodedUser match {
        case Right(u) => assert(u == expectedResult)
        case Left(error) => fail()
      }
    }
  }
}