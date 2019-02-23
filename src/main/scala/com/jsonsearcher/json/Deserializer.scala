package com.jsonsearcher.json

import com.jsonsearcher.models.{Organization, Ticket}
import cats.implicits._
import io.circe.{ACursor, Decoder}

object Deserializer {
  implicit val decodeOrg: Decoder[Organization] = DeserializationDefinition.decodeOrg
  implicit val decodeTicket: Decoder[Ticket] = DeserializationDefinition.decodeTicket
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

  } yield Organization(_id = _id,
                        url = url,
                        external_id = external_id,
                        name = name,
                        domain_names = domain_names,
                        created_at = created_at,
                        details = details,
                        shared_tickets = shared_tickets,
                        tags = tags)

  def decodeTicket(cursor: ACursor): Decoder.Result[Ticket] = for {
    _id <- cursor.get[String]("_id")
    url <- cursor.get[String]("url")
    external_id <- cursor.get[String]("external_id")
    created_at <- cursor.get[String]("created_at")
    _type <- cursor.get[Option[String]]("type")
    subject <- cursor.get[String]("subject")
    description <- cursor.get[Option[String]]("description")
    priority <- cursor.get[String]("priority")
    status <- cursor.get[String]("status")
    submitter_id <- cursor.get[Int]("submitter_id")
    assignee_id <- cursor.get[Option[Int]]("assignee_id")
    organization_id <- cursor.get[Option[Int]]("organization_id")
    tags <- cursor.get[List[String]]("tags")
    has_incidents <- cursor.get[Boolean]("has_incidents")
    due_at <- cursor.get[Option[String]]("due_at")
    via <- cursor.get[String]("via")
  } yield Ticket(_id = _id,
                  url = url,
                  external_id = external_id,
                  created_at = created_at,
                  _type = _type,
                  subject = subject,
                  description = description,
                  priority = priority,
                  status = status,
                  submitter_id = submitter_id,
                  assignee_id = assignee_id,
                  organization_id = organization_id,
                  tags = tags,
                  has_incidents = has_incidents,
                  due_at = due_at,
                  via = via)
}