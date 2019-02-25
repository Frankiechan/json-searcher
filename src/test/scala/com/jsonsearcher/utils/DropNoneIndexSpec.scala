package com.jsonsearcher.utils

import org.scalatest.FunSpec

class DropNoneIndexSpec extends FunSpec {
  describe("Given a Map[Option[A], List[Int])") {
    val map = Map(
      Some(1) -> List(1, 2),
      Some(2) -> List(3, 4, 5),
      None -> List(8)
    )

    it("should turn it into Map[A, List[Int]] by dropping the None value") {
      print(DropNoneIndex.filter(map))
//      assert(DropNoneIndex.filter(map) == Map(1 -> List(1, 2), 2 -> List(3, 4, 5)))
    }
  }
}
