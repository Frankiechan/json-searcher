package com.jsonsearcher.core.index

import org.scalatest.FunSpec

case class Something(name: String, age: Int, isCool: Boolean, gears: List[String])


class IndexerSpec extends FunSpec {

  import Somethings._

  describe("Given a List of Something") {
    val l = List(a, b, b)

    it("should index based on the function that derive index key from something") {
      val expected = Map (
        "a" -> List(0),
        "b" -> List(1,2)
      )

      assert(Indexer.index[String, Something]((s: Something) => s.name, l) == expected)
    }
  }
}

object Somethings {
  val a = Something("a", 10, true, List("a1", "a2", "b1", "c2"))
  val b = Something("b", 11, false, List("a2", "b1", "b2", "c2"))
}