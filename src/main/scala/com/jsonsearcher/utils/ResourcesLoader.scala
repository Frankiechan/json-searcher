package com.jsonsearcher.utils

import com.jsonsearcher._
import com.jsonsearcher.models.{Organization, Ticket, User}
import io.circe.parser

object ResourcesLoader {
  def load(): (Either[Throwable, List[User]], Either[Throwable, List[Ticket]], Either[Throwable, List[Organization]]) = {
    import com.jsonsearcher.json.Deserializer.{decodeTicket, decodeOrg, decodeUser}

    val decodedTickets = for {
      tickets <- FileReader.read(ResourcesConfig.DataStore.tickets)
      decodedTickets <- parser.decode[List[Ticket]](tickets)
    } yield decodedTickets

    val decodedOrgs = for {
      orgs <- FileReader.read(ResourcesConfig.DataStore.organizations)
      decodedOrgs <- parser.decode[List[Organization]](orgs)
    } yield decodedOrgs

    val decodedUsers = for {
      users <- FileReader.read(ResourcesConfig.DataStore.users)
      decodedUsers <- parser.decode[List[User]](users)
    } yield decodedUsers

    return (decodedUsers, decodedTickets, decodedOrgs)
  }
}
