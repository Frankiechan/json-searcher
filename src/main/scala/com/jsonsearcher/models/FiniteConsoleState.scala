package com.jsonsearcher.models

import com.jsonsearcher.core.SimpleSearchEngine

sealed trait FiniteConsoleState

case object Start extends FiniteConsoleState

case object Instruction extends FiniteConsoleState

case object StartSearch extends FiniteConsoleState

case class EnterSearchTerm(searchEngine: SimpleSearchEngine,
                           requestConstructor: (String, String) => SearchRequest) extends FiniteConsoleState

case class EnterSearchValue(searchEngine: SimpleSearchEngine,
                            requestConstructor: (String, String) => SearchRequest,
                            term: String) extends FiniteConsoleState
