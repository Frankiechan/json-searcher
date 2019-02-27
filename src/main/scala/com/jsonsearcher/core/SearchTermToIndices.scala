package com.jsonsearcher.core

import cats.MonadError
import cats.implicits._
import com.jsonsearcher._
import com.jsonsearcher.models.{BooleanSearchTerm, LongSearchTerm, SearchTerm, StringSearchTerm}

object ServeIndices {
  def apply[F[_]](searchTerm: SearchTerm, indexDicts: IndexDictionaries)(implicit F: MonadError[F, Throwable]): F[List[Int]] = {
    searchTerm match {
      case LongSearchTerm(term, content) =>
        searchOnDictionary[F, Long](term, content, indexDicts.longIndexDictionary)
      case StringSearchTerm(term, content) =>
        searchOnDictionary[F, String](term, content, indexDicts.stringIndexDictionary)
      case BooleanSearchTerm(term, content) =>
        searchOnDictionary[F, Boolean](term, content, indexDicts.boolIndexDictionary)
    }
  }

  def searchOnDictionary[F[_], T](term: String, content: T,
                                  indexDictionary: IndexDictionary[T])
                                 (implicit F: MonadError[F, Throwable]): F[List[Int]] = {

    /**
      * For Comprehension not be able to use that her as:
      *   F.fromOption uplift Applicative so that is not a Monad-like
      * therefore no 'withFilter' method which required by For Comprehension
      */
    val indexForTermOrNot: F[Index[T]] =
      F.fromOption(indexDictionary.get(term), NoSuchSearchIndexException(s"${term} is not a support index"))
    val indicesOrNot: F[List[Int]] = indexForTermOrNot.flatMap(index =>
      F.fromOption(index.get(content),
        NoSuchSearchValueException(s"Search value: (${content}) could not be found in key: ${term}"))
    )

    indicesOrNot
  }
}
