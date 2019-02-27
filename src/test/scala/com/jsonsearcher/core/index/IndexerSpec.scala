package com.jsonsearcher.core.index

import org.scalatest.FunSpec

case class Something(name: String, age: Int, isCool: Boolean, gears: List[String], mayHave: Option[String])


class IndexerSpec extends FunSpec {

  import Somethings._

  describe("Given a List of Something") {
    val l = List(a, b, b)

    describe("given the index key is a normal type A") {
      it("should index based on the function that derive index key A from something") {
        val expected = Map(
          "a" -> List(0),
          "b" -> List(1, 2)
        )

        assert(Indexer.index[String, Something]((s: Something) => s.name, l) == expected)
      }
    }

    describe("given the index key is a Higher Kind Option type A") {
      it("should index based on the function that derive index key A from something") {
        val expected = Map(
          "apple" -> List(0)
        )

        assert(Indexer.optionIndex[String, Something]((s: Something) => s.mayHave, l) == expected)
      }
    }

    describe("given the index key is a Higher Kind List type A") {
      it("should index based on the function that derive index key A from something") {
        val expected = Map(
          "a1" -> List(0),
          "a2" -> List(0, 1, 2),
          "b1" -> List(0, 1, 2),
          "b2" -> List(1, 2),
          "c2" -> List(0, 1, 2),
        )

        assert(Indexer.arrayIndex[String, Something]((s: Something) => s.gears, l) == expected)
      }
    }
  }
}

object Somethings {
  val a = Something("a", 10, true, List("a1", "a2", "b1", "c2"), Some("apple"))
  val b = Something("b", 11, false, List("a2", "b1", "b2", "c2"), None)
}