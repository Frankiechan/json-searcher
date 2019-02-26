package com.jsonsearcher.models

object ConsoleInteractions {

  sealed trait ConsoleInteractionStage

  case object Start extends ConsoleInteractionStage

  case object Instructions extends ConsoleInteractionStage

  case object StartSearch extends ConsoleInteractionStage

  case object ViewAvailableFields extends ConsoleInteractionStage

  case object EnterSearchTerm extends ConsoleInteractionStage

  case object EnterSearchValue extends ConsoleInteractionStage

  case object SearchCompleted extends ConsoleInteractionStage

}

