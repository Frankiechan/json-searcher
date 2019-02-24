package com.jsonsearcher

sealed trait AppError extends RuntimeException

case class NoSuchFileException(msg: String, error: Throwable) extends AppError
case class JsonStringFailedToDecodeException(error: Throwable) extends AppError
