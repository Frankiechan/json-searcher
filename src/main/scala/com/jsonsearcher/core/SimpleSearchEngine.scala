package com.jsonsearcher.core

import com.jsonsearcher.Index
import com.jsonsearcher.models._

case class Indices(longIndices: Map[String, Index[Long]],
                   StringIndices: Map[String, Index[String]],
                   BooleanIndices: Map[String, Index[Boolean]])

class SimpleSearchEngine(private val views: List[View], private val indices: Indices) {

  def search(searchTerm: SearchTerm): List[Option[View]] = {

    val positionsOrNot: Option[List[Int]] = searchTerm match {
      case st: StringSearchTerm => indices.StringIndices.get(st.term).flatMap(_.get(st.content))
      case st: LongSearchTerm => indices.longIndices.get(st.term).flatMap(_.get(st.content))
    }

    positionsOrNot match {
      case Some(l) => l.map(views.lift(_))
      case None => List.empty
    }
  }
}

object SimpleSearchEngine {
  def apply(views: List[View], indices: Indices): SimpleSearchEngine = new SimpleSearchEngine(views, indices)
}