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

  def optionIndex[A, T](f: T => Option[A], origin: List[T]): Index[A] = {
    origin
      .map(f(_))
      .zipWithIndex
      .foldRight(Map[A, List[Int]]()) {
        case ((key, index), map) =>
          key match {
            case Some(v) => map updated(v, index :: map.getOrElse(v, Nil))
            case None => map
          }
      }
  }

  def arrayIndex[A, T](f: T => List[A], origin: List[T]): Index[A] = {
    origin
      .map(f(_))
      .zipWithIndex
      .foldRight(Map[A, List[Int]]()) {
        case ((key, index), map) =>
          key.foldRight(map){
            case (key, map) => map updated(key, index :: map.getOrElse(key, Nil))
          }
      }
  }
}

object ListIndexer {

}