package com.jsonsearcher.core

import cats.data.NonEmptyList
import cats.implicits._
import com.jsonsearcher.core.index.UserViewIndices
import com.jsonsearcher.fixtures.ModelData
import com.jsonsearcher.models.{LongSearchTerm, SearchStore}
import org.scalatest.FunSpec

class SearchOperationSpec extends FunSpec {
  describe("Given a search store") {
    import ModelData._
    val userView = UserViewGenerator.generate((List(user), List(ticket), List(org)))
    val searchStore = SearchStore(userView, UserViewIndices.preload(userView))

    describe("Given a search term for a searchable key") {
      val searchTerm = LongSearchTerm("_id", 69)

      it("should be able to search the related info based on the searchTerm") {
        type ThrowableOr[A] = Either[Throwable, A]

        val expectedResult = NonEmptyList.fromListUnsafe(userView.filter(_.user._id == 69))

        val searchResult = SearchOperation[ThrowableOr](searchTerm, searchStore)

        assert(searchResult == Right(expectedResult))

      }
    }
  }
}
