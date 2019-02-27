package com.jsonsearcher.core.index

import com.jsonsearcher.Index

object Indexer {
  def index[A, T](f: T => A, origin: List[T]): Index[A] = {
    origin
      .map(f(_))
      .zipWithIndex
      .foldRight(Map[A, List[Int]]()) {
        case ((key, index), map) =>
          map updated(key, index :: map.getOrElse(key, Nil))
      }
  }
}

object ListIndexer {

}