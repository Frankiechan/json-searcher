package com.jsonsearcher.utils

import cats.effect.Sync
import cats.implicits._
import com.jsonsearcher.ResourcesConfig
import com.jsonsearcher.models.{Organization, Ticket, User}
import io.circe.parser

object ResourcesLoader {
  def load[F[_]]()(implicit F: Sync[F]): (F[List[User]], F[List[Ticket]], F[List[Organization]]) = {
    import com.jsonsearcher.json.Deserializer.{decodeOrg, decodeTicket, decodeUser}

    val decodedUsersOrNot: F[List[User]] = for {
      jsonStr <- FileReader.read[F](ResourcesConfig.DataStore.users)
      users <- F.fromEither(parser.decode[List[User]](jsonStr))
    } yield users

    val decodedTicketsOrNot: F[List[Ticket]] = for {
      jsonStr <- FileReader.read[F](ResourcesConfig.DataStore.tickets)
      tickets <- F.fromEither(parser.decode[List[Ticket]](jsonStr))
    } yield tickets


    val decodedOrgsOrNot: F[List[Organization]] = for {
      jsonStr <- FileReader.read[F](ResourcesConfig.DataStore.organizations)
      orgs <- F.fromEither(parser.decode[List[Organization]](jsonStr))
    } yield orgs

    return (decodedUsersOrNot, decodedTicketsOrNot, decodedOrgsOrNot)
  }
}
