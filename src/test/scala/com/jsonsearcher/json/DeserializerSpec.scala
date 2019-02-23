package com.jsonsearcher.json

import org.scalatest._
import io.circe.parser
import com.jsonsearcher.fixtures.JsonString
import com.jsonsearcher.models.Organization


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

}