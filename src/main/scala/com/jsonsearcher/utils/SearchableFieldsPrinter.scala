package com.jsonsearcher.utils

import cats.Monad
import cats.implicits._
import com.jsonsearcher.models.{Organization, Ticket, User}

case class SearchableFieldsPrinter[F[_]]()(implicit F: Monad[F]) {
  def printUserSearchableField(): F[Unit] =
    F.pure(println("User Searchable Fields:")) >> F.pure(extractFields[User].foreach(println(_)))

  def printTicketSearchableField(): F[Unit] =
    F.pure(println("Ticket Searchable Fields:")) >> F.pure(extractFields[Ticket].foreach(println(_)))

  def printOrgSearchableField(): F[Unit] =
    F.pure(println("Organization Searchable Fields:")) >> F.pure(extractFields[Organization].foreach(println(_)))

  private def extractFields[T <: Product : Manifest](): Array[String] =
    implicitly[Manifest[T]].runtimeClass.getDeclaredFields.map(_.getName)
}