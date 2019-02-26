package com.jsonsearcher.utils

import cats.effect.Sync
import cats.{Monad, MonadError}
import cats.implicits._
import com.jsonsearcher.models.{IntSearchTerm, SearchTerm, StringSearchTerm, UserSearchRequest}

class UserSearchRequestBuilder[F[_]](private var term: String = "", private var content: String ="")(implicit F: Sync[F]) {
  def withTerm(term: String): UserSearchRequestBuilder[F] = {
    this.term = term
    this
  }

  def withContent(content: String): UserSearchRequestBuilder[F] = {
    this.content = content
    this
  }

  def build(): F[UserSearchRequest] = {
    val searchTerm: F[SearchTerm] = term match {
      case "_id" => F.pure(this.content.toInt).map(i => IntSearchTerm(this.term, i))
      case "url" => F.pure(StringSearchTerm(this.term, this.content))
      case _ => F.raiseError(new RuntimeException(s"${term} is not a supported SearchField"))
    }

    for {
      st <- searchTerm
      request <- F.pure(UserSearchRequest(st))
    } yield request
  }
}