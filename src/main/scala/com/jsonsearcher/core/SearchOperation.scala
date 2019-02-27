package com.jsonsearcher.core

import cats.MonadError
import cats.data.NonEmptyList
import cats.implicits._
import com.jsonsearcher.NoSuchSearchValueException
import com.jsonsearcher.models._

object SearchOperation {
  def apply[F[_]](searchTerm: SearchTerm, searchStore: SearchStore)(implicit F: MonadError[F, Throwable]): F[NonEmptyList[View]] = {
    val indicesOrNot: F[List[Int]] = ServeIndices[F](searchTerm, searchStore.indexDictionaries)
    /**
      * For Comprehension not be able to use that her as:
      *   F.fromOption uplift Applicative so that is not a Monad-like
      *   therefore no 'withFilter' method which required by For Comprehension
      */
    val nonEmptyResults = indicesOrNot
      .flatMap(indices => F.pure(indices.map(searchStore.views.lift(_)).flatten))
      .flatMap(
        resultsOrEmpty => F.fromOption(
          NonEmptyList.fromList(resultsOrEmpty),
          NoSuchSearchValueException(
            s"Search value: (${SearchTermHelper.content2String(searchTerm)}) could not be found in key: ${searchTerm.term}")))

    nonEmptyResults
  }
}
