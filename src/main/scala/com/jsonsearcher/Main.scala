package com.jsonsearcher

import com.jsonsearcher.models.{Organization, Ticket, User}
import com.jsonsearcher.utils.FileReader
import io.circe._


object Main extends App {

  val tickets = FileReader.read(ResourcesConfig.DataStore.tickets)

  import com.jsonsearcher.json.Deserializer.decodeTicket
  val decodedTickets = parser.decode[List[Ticket]](tickets)

  decodedTickets match {
    case Right(l) => l.foreach(println(_))
    case Left(e) => println(e)
  }

  val ogs = FileReader.read(ResourcesConfig.DataStore.organizations)

  import com.jsonsearcher.json.Deserializer.decodeOrg
  val decodedOrg = parser.decode[List[Organization]](ogs)

  decodedOrg match {
    case Right(l) => l.foreach(println(_))
    case Left(e) => println(e)
  }

  val users = FileReader.read(ResourcesConfig.DataStore.users)

  import com.jsonsearcher.json.Deserializer.decodeUser
  val decodedUsers = parser.decode[List[User]](users)

  decodedUsers match {
    case Right(l) => l.foreach(println(_))
    case Left(e) => println(e)
  }

}
