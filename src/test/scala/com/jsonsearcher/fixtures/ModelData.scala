package com.jsonsearcher.fixtures

import com.jsonsearcher.models.{Organization, Ticket, User}

object ModelData {

  val org = Organization(
    _id = 101,
    url = "http://initech.zendesk.com/api/v2/organizations/101.json",
    external_id = "9270ed79-35eb-4a38-a46f-35725197ea8d",
    name = "Enthaze",
    domain_names = List("kage.com", "ecratic.com", "endipin.com", "zentix.com"),
    created_at = "2016-05-21T11:10:28 -10:00",
    details = "MegaCorp",
    shared_tickets = false,
    tags = List("Fulton", "West", "Rodriguez", "Farley"))

  val ticket = Ticket(
    _id = "436bf9b0-1147-4c0a-8439-6f79833bff5b",
    url = "http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json",
    external_id = "9210cdc9-4bee-485f-a078-35396cd74063",
    created_at = "2016-04-28T11:19:34 -10:00",
    _type = Some("incident"),
    subject = "A Catastrophe in Korea (North)",
    description = Some("Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum."),
    priority = "high",
    status = "pending",
    submitter_id = 69,
    assignee_id = Some(69),
    organization_id = Some(101),
    tags = List("abc"),
    has_incidents = false,
    due_at = Some("2016-07-31T02:37:50 -10:00"),
    via = "web")

  val user = User(
    _id = 69,
    url = "http://initech.zendesk.com/api/v2/users/69.json",
    external_id = "0320fff9-8a1f-4654-a8d5-dbf3c9c79591",
    name = "Velasquez Cameron,Some(Miss Janette)",
    alias = Some("xxx"),
    created_at = "2016-04-04T03:34:12 -10:00",
    active = true,
    verified = Some(false),
    shared = true,
    locale = Some("en-AU"),
    timezone = Some("Cyprus"),
    last_login_at = "2015-02-02T02:22:18 -11:00",
    email = Some("janettecameron@flotonic.com"),
    phone = Some("9494-843-571"),
    signature = "Don't Worry Be Happy!",
    organization_id = Some(101),
    tags = List("bbc"),
    suspended = true,
    role = "end-user")

}
