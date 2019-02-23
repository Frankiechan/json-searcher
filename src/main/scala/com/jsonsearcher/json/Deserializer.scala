package com.jsonsearcher.json

import com.jsonsearcher.models.Organization
import io.circe.{ACursor, Decoder}

object Deserializer {
  implicit val decodeOrg: Decoder[Organization] = DeserializationDefinition.decodeOrg
}

object DeserializationDefinition {
  def decodeOrg(cursor: ACursor): Decoder.Result[Organization] = for {
    _id <- cursor.get[Int]("_id")
    url <- cursor.get[String]("url")
    external_id <- cursor.get[String]("external_id")
    name <- cursor.get[String]("name")
    domain_names <- cursor.get[List[String]]("domain_names")
    created_at <- cursor.get[String]("created_at")
    details <- cursor.get[String]("details")
    shared_tickets <- cursor.get[Boolean]("shared_tickets")
    tags <- cursor.get[List[String]]("tags")

  } yield Organization(_id, url, external_id, name, domain_names, created_at, details, shared_tickets, tags)

}