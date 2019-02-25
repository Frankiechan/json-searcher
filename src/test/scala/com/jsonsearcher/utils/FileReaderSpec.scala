//package com.jsonsearcher.utils
//
//import java.nio.file.Paths
//
//import org.scalatest.FunSpec
//
//class FileReaderSpec extends FunSpec {
//  describe("Given a file location") {
//    val cwd = Paths.get(System.getProperty("user.dir"))
//    val testFilePath = Paths.get(cwd.toString, "src", "test", "scala", "com", "jsonsearcher", "staticfiles", "test.json")
//
//    it("should read the file into String") {
//      val expectedResult = "A Test File"
//      val content = FileReader.read(testFilePath)
//
//      assert(content == expectedResult)
//    }
//  }
//}
//
//
