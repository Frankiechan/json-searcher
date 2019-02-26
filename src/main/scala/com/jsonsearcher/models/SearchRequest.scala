package com.jsonsearcher.models

import cats.effect.Sync
import cats.implicits._
import com.jsonsearcher.{AppError, NoSuchSearchFieldException, ToLongParseErrorMakeIncompatibleSearchValue}

sealed trait SearchRequest

case class UserSearchRequest(term: String, content: String) extends SearchRequest

case class TicketSearchRequest(term: String, content: String) extends SearchRequest

case class OrgSearchRequest(term: String, content: String) extends SearchRequest

object SearchTermBuilder {
  def build[F[_]](searchRequest: SearchRequest)(implicit F: Sync[F]): F[SearchTerm] = {
    searchRequest match {
      case UserSearchRequest(t, c) => UserSearchTermBuilder.build(t)(c)
      case TicketSearchRequest(t, c) => ???
      case TicketSearchRequest(t, c) => ???
    }
  }
}

object UserSearchTermBuilder {
  def build[F[_]](term: String)(content: String)(implicit F: Sync[F]): F[SearchTerm] = {
    val searchTerm: F[SearchTerm] = term match {
      case "_id" => F.delay(content.toInt).map(i => LongSearchTerm(term, i))
      case "url" => F.pure(StringSearchTerm(term, content))
      case _ => F.raiseError(NoSuchSearchFieldException(s"${term} is not a support SearchField"))
    }

    BuilderErrorEnricher[F, SearchTerm](searchTerm)
  }
}


object TicketrTermRequestBuilder {
  def build[F[_]](term: String)(content: String)(implicit F: Sync[F]): F[SearchTerm] = {
    ???
  }
}

object OrgsSearchTermBuilder {
  def build[F[_]](term: String)(content: String)(implicit F: Sync[F]): F[SearchTerm] = {
    ???
  }
}

object BuilderErrorEnricher {
  def apply[F[_], T](request: F[T])(implicit F: Sync[F]): F[T] = {
    request.handleErrorWith(e => e match {
      case e: AppError => F.raiseError(e)
      case e: NumberFormatException =>
        F.raiseError(ToLongParseErrorMakeIncompatibleSearchValue(s"Search Value should be Long but parsing failed: ${e.getMessage}", e))
      case e: Throwable => F.raiseError(e)
    })
  }
}