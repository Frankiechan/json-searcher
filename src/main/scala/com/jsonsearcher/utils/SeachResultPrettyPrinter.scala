package com.jsonsearcher.utils

import cats.Monad
import com.jsonsearcher.models.View

object SearchResultPrettyPrinter {

  def print[F[_]](searchResults: List[View])(implicit F: Monad[F]): F[Unit] = {
    import io.circe.generic.auto._
    import io.circe.syntax._

    F.pure(searchResults.map(_.asJson).foreach(println(_)))
  }
}
