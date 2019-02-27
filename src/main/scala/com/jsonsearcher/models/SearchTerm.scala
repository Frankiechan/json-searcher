package com.jsonsearcher.models

sealed trait SearchTerm {
  def term: String
}

case class StringSearchTerm(term: String, content: String) extends SearchTerm

case class LongSearchTerm(term: String, content: Long) extends SearchTerm

case class BooleanSearchTerm(term: String, content: Boolean) extends SearchTerm

object SearchTermHelper {
  def content2String(searchTerm: SearchTerm): String = {
    searchTerm match {
      case StringSearchTerm(_, c) => c
      case LongSearchTerm(_, c) => c.toString
      case BooleanSearchTerm(_, c) => c.toString
    }
  }
}