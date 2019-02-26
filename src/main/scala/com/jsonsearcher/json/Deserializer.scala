package com.jsonsearcher.json

import com.jsonsearcher.models.{Organization, Ticket, User}
import io.circe.{ACursor, Decoder}

object Deserializer {
  implicit val decodeOrg: Decoder[Organization] = DeserializationDefinition.decodeOrg
  implicit val decodeTicket: Decoder[Ticket] = DeserializationDefinition.decodeTicket
  implicit val decodeUser:Decoder[User] = DeserializationDefinition.decodeUser
}

object DeserializationDefinition {
  def decodeOrg(cursor: ACursor): Decoder.Result[Organization] = for {
    _id <- cursor.get[Long]("_id")
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
    submitter_id <- cursor.get[Long]("submitter_id")
    assignee_id <- cursor.get[Option[Long]]("assignee_id")
    organization_id <- cursor.get[Option[Long]]("organization_id")
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

  def decodeUser(cursor: ACursor): Decoder.Result[User] = for {
    _id <- cursor.get[Long]("_id")
    url <- cursor.get[String]("url")
    external_id <- cursor.get[String]("external_id")
    name <- cursor.get[String]("name")
    alias <- cursor.get[Option[String]]("alias")
    created_at <- cursor.get[String]("created_at")
    active <- cursor.get[Boolean]("active")
    verified <- cursor.get[Option[Boolean]]("verified")
    shared <- cursor.get[Boolean]("shared")
    locale <- cursor.get[Option[String]]("locale")
    timezone <- cursor.get[Option[String]]("timezone")
    last_login_at <- cursor.get[String]("last_login_at")
    email <- cursor.get[Option[String]]("email")
    phone <- cursor.get[Option[String]]("phone")
    signature <- cursor.get[String]("signature")
    organization_id <- cursor.get[Option[Long]]("organization_id")
    tags <- cursor.get[List[String]]("tags")
    suspended <- cursor.get[Boolean]("suspended")
    role <- cursor.get[String]("role")
  } yield User(_id = _id,
    url = url,
    external_id = external_id,
    name = name,
    alias = alias,
    created_at = created_at,
    active = active,
    verified = verified,
    shared = shared,
    locale = locale,
    timezone = timezone,
    last_login_at = last_login_at,
    email = email,
    phone = phone,
    signature = signature,
    organization_id = organization_id,
    tags = tags,
    suspended = suspended,
    role = role
  )
}