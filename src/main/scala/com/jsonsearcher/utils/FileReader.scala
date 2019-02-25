package com.jsonsearcher.utils

import java.io.{FileInputStream, InputStream}
import java.nio.file.Path

import cats.MonadError
import cats.implicits._
import com.jsonsearcher.{AppError, NoSuchFileException}

import scala.util.Try


object FileReader {
  def read[F[_]](filePath: Path)(implicit F: MonadError[F, Throwable]): F[String] = {
    val fileOrNot = F.pure(new FileInputStream(filePath.toString))
    val contentOrNot = fileOrNot.flatMap(fs => F.pure(scala.io.Source.fromInputStream(fs).mkString))
    contentOrNot.attempt.flatMap {
      case Right(c) => F.pure(c)
      case Left(e) =>
        val msg = s"Could not find file in path: ${filePath}"
        F.raiseError(NoSuchFileException(msg, e))
    }
  }
}


