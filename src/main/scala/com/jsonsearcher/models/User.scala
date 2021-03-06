package com.jsonsearcher.models

case class User(_id: Long,
                url: String,
                external_id: String,
                name: String,
                alias: Option[String],
                created_at: String,
                active: Boolean,
                verified: Option[Boolean],
                shared: Boolean,
                locale: Option[String],
                timezone: Option[String],
                last_login_at: String,
                email: Option[String],
                phone: Option[String],
                signature: String,
                organization_id: Option[Long],
                tags: List[String],
                suspended: Boolean,
                role: String
               )



