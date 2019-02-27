package com.jsonsearcher.models

sealed trait FiniteConsoleState

case object Start extends FiniteConsoleState

case object Instruction extends FiniteConsoleState

case object StartSearch extends FiniteConsoleState

case class EnterSearchTerm(searchStore: SearchStore,
                           requestConstructor: (String, String) => SearchRequest) extends FiniteConsoleState

case class EnterSearchValue(searchStore: SearchStore,
                            requestConstructor: (String, String) => SearchRequest,
                            term: String) extends FiniteConsoleState
