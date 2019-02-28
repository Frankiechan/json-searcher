package com.jsonsearcher.utils

import java.nio.file.Paths

import cats.implicits._
import org.scalatest.FunSpec

class FileReaderSpec extends FunSpec {
  describe("Given a file location") {
    val cwd = Paths.get(System.getProperty("user.dir"))
    val testFilePath = Paths.get(cwd.toString, "src", "test", "scala", "com", "jsonsearcher", "staticfiles", "test.json")

    it("should read the file into String") {
      type ThrowableOrNot[A] = Either[Throwable, A]
      val expectedResult = "A Test File"
      val content = FileReader.read[ThrowableOrNot](testFilePath)

      assert(content == Right(expectedResult))
    }
  }
}


