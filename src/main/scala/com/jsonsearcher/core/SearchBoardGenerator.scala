package com.jsonsearcher.core

import com.jsonsearcher.models.{Indices, SearchBoard, View}

object SearchBoardGenerator {
  def generate(views: List[View], f: List[View] => Indices): SearchBoard[View] = SearchBoard(views, f(views))
}