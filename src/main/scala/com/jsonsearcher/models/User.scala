package com.jsonsearcher.models

case class User(_id: Int,
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
                signiture: String,
                organization_id: Option[Int],
                tags: List[String],
                suspended: Boolean,
                role: String
               )



