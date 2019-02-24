package com.jsonsearcher.utils

import java.io.{FileInputStream, InputStream}
import java.nio.file.Path


object FileReader {
  def read(filePath: Path): String = {
    val stream: InputStream = new FileInputStream(filePath.toString)
    val contents = scala.io.Source.fromInputStream(stream).mkString

    return contents
  }
}


