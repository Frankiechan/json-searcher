package com.jsonsearcher

import cats.effect.Sync

sealed trait AppError extends Throwable {
  def msg: String
}

case class NoSuchFileException(msg: String, error: Throwable) extends AppError

case class JsonStringFailedToDecodeException(msg: String, error: Throwable) extends AppError

case class NoSuchSearchValueException(msg: String) extends AppError

case class NoSuchSearchFieldException(msg: String) extends AppError

case class NoSuchDictionaryTypeException(msg: String) extends AppError

case class ToLongParseErrorMakeIncompatibleSearchValue(msg: String, error: Throwable) extends AppError



object ErrorHandler {
  def handle[F[_]](throwable: Throwable)(implicit F: Sync[F]): F[Unit] = {
    val logError = throwable match {
      case e: AppError => F.pure(println(s"[Error]: ${e.msg}"))
      case e: Throwable => F.pure(println(s"[Unexpected Fetal Error]: ${e}"))
    }

    logError
  }
}