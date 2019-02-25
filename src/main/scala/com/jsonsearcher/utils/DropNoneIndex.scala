package com.jsonsearcher.utils

object DropNoneIndex {
  def filter[A](index: Map[Option[A], List[Int]]): Map[A, List[Int]] = {
    index.withFilter(i => {
      i._1 match {
        case Some(v) => true
        case None => false
      }
    }).map{case (Some(v), l: List[Int]) => (v, l)}
  }
}
