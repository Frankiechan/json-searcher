package com.jsonsearcher.core

import com.jsonsearcher.Index
import com.jsonsearcher.models.{Indices, View}
import cats.data.OptionT
import cats.implicits._

//case class Indices(intIndices: Map[String, Index[Int]],
//                   StringIndices: Map[String, Index[String]],
//                   BooleanIndices: Map[String, Index[Boolean]])

class SimpleSearchEngine(private val views: List[View], private val indices: Indices) {

  def search[T](searchTerm: SearchTerm[T], f: (SearchTerm[T], Indices, List[View]) => Index[T]): List[View] = {

    val results = for {
      viewIndices <- f(searchTerm).get(searchTerm.content)
      searchResult <- viewIndices.map(views.lift(_)).flatten
    } yield searchResult

    results.toList
  }

  def findIndexInPreload[T](searchTerm: SearchTerm[T], indices: Indices): Option[Index[_]] = {
    searchTerm match {
      case StringSearchTerm(term, content) => indices.StringIndices.get(term)
      case IntSearchTerm(term, content) => indices.intIndices.get(term)
    }
  }

  def adhocIndex[T](searchTerm: SearchTerm[T], views: List[View]): Option[Index[_]] = {
    searchTerm match {
      case StringSearchTerm(term, content) => Indexer.index[T, View]
      case IntSearchTerm(term, content) => indices.intIndices.get(term)
    }
  }
}

trait SearchResult

trait SearchTerm[T] {
  val term: String
  val content: T
}

case class StringSearchTerm(term: String, content: String) extends SearchTerm[String]
case class IntSearchTerm(term: String, content: Int) extends SearchTerm[Int]

case class UserViewSearchSpec[T](f: Indices => Index[T], adhocIndexf: List[View] => Index[T])

object SearchTerm2UserSearchSpec {
  def apply[T](searchTerm: SearchTerm[T]): UserViewSearchSpec[T] = {
    searchTerm match {
      case StringSearchTerm(term, content) =>
        term match {
          case "url" => UserViewSearchSpec()
        }
    }
  }
}

object Tryitout extends App {

  val st = IntSearchTerm("_id", 1)

}