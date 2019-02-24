package com.jsonsearcher.core

object Indexer {
  def index[A, T](f: T => A, origin: List[T]): Map[A, List[Int]] = {
    origin
      .map(f(_))
      .zipWithIndex
      .foldRight(Map[A, List[Int]]()) {
        case ((key, index), map) =>
          map updated(key, index :: map.getOrElse(key, Nil))
      }
  }
}
