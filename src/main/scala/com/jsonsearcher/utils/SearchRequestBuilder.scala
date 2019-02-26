package com.jsonsearcher.utils

import cats.effect.Sync
import cats.implicits._
import com.jsonsearcher.models.{IntSearchTerm, SearchTerm, StringSearchTerm, UserSearchRequest}

object UserSearchRequestBuilder {
  def build[F[_]](term: String)(content: String)(implicit F: Sync[F]): F[UserSearchRequest] = {
    val searchTerm: F[SearchTerm] = term match {
      case "_id" => F.pure(content.toInt).map(i => IntSearchTerm(term, i))
      case "url" => F.pure(StringSearchTerm(term, content))
      case _ => F.raiseError(new RuntimeException(s"${term} is not a supported SearchField"))
    }

    for {
      st <- searchTerm
      request <- F.pure(UserSearchRequest(st))
    } yield request
  }
}