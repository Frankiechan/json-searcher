package com.jsonsearcher.utils

import java.io.{FileInputStream, InputStream}
import java.nio.file.Path

import com.jsonsearcher.{AppError, NoSuchFileException}

import scala.util.Try


object FileReader {
  def read(filePath: Path): Either[AppError, String] = {
    Try {
      val stream: InputStream = new FileInputStream(filePath.toString)
      val contents = scala.io.Source.fromInputStream(stream).mkString

      contents
    }.toEither match {
      case Left(e) =>
        val msg = s"Could not find file in path: ${filePath}"
        Left(NoSuchFileException(msg, e))
      case Right(v) => Right(v)
    }
  }
}


