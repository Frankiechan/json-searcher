package com.jsonsearcher.models

sealed trait SearchTerm
case class StringSearchTerm(term: String, content: String) extends SearchTerm
case class LongSearchTerm(term: String, content: Long) extends SearchTerm