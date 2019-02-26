package com.jsonsearcher.models

case class Organization(_id: Long,
                        url: String,
                        external_id: String,
                        name: String,
                        domain_names: List[String],
                        created_at: String,
                        details: String,
                        shared_tickets: Boolean,
                        tags: List[String]
                       )

