package com.jsonsearcher.models

import com.jsonsearcher.Index

case class Indices(intIndices: Map[String, Index[Int]],
                   StringIndices: Map[String, Index[String]],
                   BooleanIndices: Map[String, Index[Boolean]])

case class SearchBoard[V <: View](viewLikeContents: List[V], indices: Indices)
