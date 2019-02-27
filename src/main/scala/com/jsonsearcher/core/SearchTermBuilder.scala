package com.jsonsearcher.core

import cats.effect.Sync
import com.jsonsearcher.core.UnsafeBuilderFunc._
import com.jsonsearcher.models._
import com.jsonsearcher.{NoSuchSearchFieldException, ToBooleanParseErrorMakeIncompatibleSearchValue, ToLongParseErrorMakeIncompatibleSearchValue}

object SearchTermBuilder {
  def build[F[_]](searchRequest: SearchRequest)(implicit F: Sync[F]): F[SearchTerm] = {
    searchRequest match {
      case UserSearchRequest(t, c) => UserSearchTermBuilder.build(t)(c)
      case TicketSearchRequest(t, c) => TicketSearchTermBuilder.build(t)(c)
      case OrgSearchRequest(t, c) => OrgsSearchTermBuilder.build(t)(c)
    }
  }
}

object UserSearchTermBuilder {
  def build[F[_]](term: String)(content: String)(implicit F: Sync[F]): F[SearchTerm] = {
    F.delay(
      term match {
        case "_id" | "organization_id" => longSearchTerm(term, content)
        case "url" | "external_id" | "name" | "alias"
             | "created_at" | "locale" | "timezone" | "last_login_at"
             | "email" | "phone" | "signature" | "tags" => StringSearchTerm(term, content)
        case "active" | "verified" | "shared" | "suspended" => booleanSearchTerm(term, content)
        case _ => throw NoSuchSearchFieldException(s"${term} is not a support SearchField")
      }
    )
  }
}


object TicketSearchTermBuilder {
  def build[F[_]](term: String)(content: String)(implicit F: Sync[F]): F[SearchTerm] = {
    F.delay(
      term match {
        case "submitter_id" | "assignee_id" | "organization_id" => longSearchTerm(term, content)
        case "_id" | "url" | "external_id" | "created_at" | "subject"
             | "description" | "priority" | "status" | "tags" | "type"
             | "due_at" | "via" => StringSearchTerm(term, content)
        case "has_incidents" => booleanSearchTerm(term, content)
        case _ => throw NoSuchSearchFieldException(s"${term} is not a support SearchField")
      }
    )
  }
}

object OrgsSearchTermBuilder {
  def build[F[_]](term: String)(content: String)(implicit F: Sync[F]): F[SearchTerm] = {
    F.delay(
      term match {
        case "_id" => longSearchTerm(term, content)
        case "url" | "external_id" | "name" | "domain_names"
             | "created_at" | "details" | "tags" => StringSearchTerm(term, content)
        case "shared_tickets" => booleanSearchTerm(term, content)
        case _ => throw NoSuchSearchFieldException(s"${term} is not a support SearchField")
      }
    )
  }
}

object UnsafeBuilderFunc {
  def longSearchTerm(term: String, content: String): LongSearchTerm = {
    try {
      LongSearchTerm(term, content.toLong)
    } catch {
      case e: Throwable =>
        throw ToLongParseErrorMakeIncompatibleSearchValue(s"Search Value should be Long but parsing failed: ${e.getMessage}", e)
    }
  }


  def booleanSearchTerm(term: String, content: String): BooleanSearchTerm = {
    try {
      BooleanSearchTerm(term, content.toBoolean)
    } catch {
      case e: Throwable =>
        throw ToBooleanParseErrorMakeIncompatibleSearchValue(s"Search Value should be Boolean but parsing failed: ${e.getMessage}", e)
    }
  }
}
