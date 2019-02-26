package com.jsonsearcher.models

sealed trait SearchTerm
case class StringSearchTerm(term: String, content: String) extends SearchTerm
case class IntSearchTerm(term: String, content: Int) extends SearchTerm

