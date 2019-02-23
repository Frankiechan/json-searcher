package com.jsonsearcher.models

case class Ticket(_id: String,
                  url: String,
                  external_id: String,
                  created_at: String,
                  `type`: Option[String],
                  subject: String,
                  description: Option[String],
                  priority: String,
                  status: String,
                  submitter_id: Int,
                  assignee_id: Option[Int],
                  organization_id: Option[Int],
                  tags: List[String],
                  has_incidents: Boolean,
                  due_at: Option[String],
                  via: String
                 )