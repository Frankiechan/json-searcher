package com.jsonsearcher

import com.jsonsearcher.models._
import com.jsonsearcher.utils.FileReader
import io.circe._
import cats.implicits._
import com.jsonsearcher.core.{Indexer, OrganizationViewGenerator, TicketViewGenerator, UserViewGenerator}


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
    case Right(l) => println(l.groupBy(_._id))
    case Left(e) => println(e)
  }

  (decodedUsers, decodedTickets, decodedOrg).mapN((a, b, c) => {
    val userView = UserViewGenerator.generate((a, b, c))

    val userIdIndexedView = Indexer.index[Int, UserView]((uv: UserView) => uv.user._id, userView)

    val userSearchResults = userIdIndexedView.get(69) match {
      case Some(is) => is.map(userView.get(_))
      case None => List.empty
    }

    import io.circe.generic.auto._
    import io.circe.syntax._

    println("User")
    userSearchResults.foreach {
      case Some(v) => println(v.asJson)
    }

    val orgView = OrganizationViewGenerator.generate((a, b, c))
    val orgIdIndexedView = Indexer.index[Int, OrganizationView]((ov: OrganizationView) => ov.org._id, orgView)

    val orgSearchResults = orgIdIndexedView.get(101) match {
      case Some(is) => is.map(orgView.get(_))
      case None => List.empty
    }

    println("Org")
    orgSearchResults.foreach {
      case Some(v) => println(v.asJson)
    }

    val ticketView = TicketViewGenerator.generate((a, b, c))
    val ticketIdIndexedView = Indexer.index[String, TicketView]((t: TicketView) => t.ticket._id, ticketView)

    val ticketSearchResults = ticketIdIndexedView.get("436bf9b0-1147-4c0a-8439-6f79833bff5b") match {
      case Some(is) => is.map(ticketView.get(_))
      case None => List.empty
    }

    println("Tickets")
    ticketSearchResults.foreach {
      case Some(v) => println(v.asJson)
    }
  })
}
