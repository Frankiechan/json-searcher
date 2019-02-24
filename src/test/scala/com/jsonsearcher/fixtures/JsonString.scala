package com.jsonsearcher.fixtures

object JsonString {

  val org =
    """
      |{
      |    "_id": 101,
      |    "url": "http://initech.zendesk.com/api/v2/organizations/101.json",
      |    "external_id": "9270ed79-35eb-4a38-a46f-35725197ea8d",
      |    "name": "Enthaze",
      |    "domain_names": [
      |      "kage.com",
      |      "ecratic.com",
      |      "endipin.com",
      |      "zentix.com"
      |    ],
      |    "created_at": "2016-05-21T11:10:28 -10:00",
      |    "details": "MegaCorp",
      |    "shared_tickets": false,
      |    "tags": [
      |      "Fulton",
      |      "West",
      |      "Rodriguez",
      |      "Farley"
      |    ]
      |}
    """.stripMargin

  val ticket =
    """
      |{
      |    "_id": "50dfc8bc-31de-411e-92bf-a6d6b9dfa490",
      |    "url": "http://initech.zendesk.com/api/v2/tickets/50dfc8bc-31de-411e-92bf-a6d6b9dfa490.json",
      |    "external_id": "8bc8bee7-2d98-4b69-b4a9-4f348ff41fa3",
      |    "created_at": "2016-03-08T09:44:54 -11:00",
      |    "type": "task",
      |    "subject": "A Problem in South Africa",
      |    "description": "Esse nisi occaecat pariatur veniam culpa dolore anim elit aliquip. Cupidatat mollit nulla consectetur ullamco tempor esse.",
      |    "priority": "high",
      |    "status": "hold",
      |    "submitter_id": 43,
      |    "assignee_id": 54,
      |    "organization_id": 103,
      |    "tags": [
      |      "Georgia",
      |      "Tennessee",
      |      "Mississippi",
      |      "Marshall Islands"
      |    ],
      |    "has_incidents": true,
      |    "due_at": "2016-08-03T09:17:37 -10:00",
      |    "via": "voice"
      |}
    """.stripMargin

  val user =
    """
      |{
      |    "_id": 1,
      |    "url": "http://initech.zendesk.com/api/v2/users/1.json",
      |    "external_id": "74341f74-9c79-49d5-9611-87ef9b6eb75f",
      |    "name": "Francisca Rasmussen",
      |    "alias": "Miss Coffey",
      |    "created_at": "2016-04-15T05:19:46 -10:00",
      |    "active": true,
      |    "verified": true,
      |    "shared": false,
      |    "locale": "en-AU",
      |    "timezone": "Sri Lanka",
      |    "last_login_at": "2013-08-04T01:03:27 -10:00",
      |    "email": "coffeyrasmussen@flotonic.com",
      |    "phone": "8335-422-718",
      |    "signature": "Don't Worry Be Happy!",
      |    "organization_id": 119,
      |    "tags": [
      |      "Springville",
      |      "Sutton",
      |      "Hartsville/Hartley",
      |      "Diaperville"
      |    ],
      |    "suspended": true,
      |    "role": "admin"
      |  }
    """.stripMargin
}
