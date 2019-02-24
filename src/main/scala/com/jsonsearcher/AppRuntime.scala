package com.jsonsearcher

import atto._
import Atto._
import atto.ParseResult.{Done, Fail}
import cats.implicits._

import scala.annotation.tailrec

object AppRuntime extends App {

  def openning() = println("type quit to exist at anytime, Press 'Enter' to continue")

  def moreInfo() = println(
    """
      |Select Search Options:
      |* Press 1 to search Zendesk
      |* Press 2 to view a list of searchable fields
      |* Type `quit` to exit
      |
    """.stripMargin
  )

  def searchInstruction() = println(
    """
      |1) Users or 2) Tickets or 3) Organizations
    """.stripMargin
  )

  def searchableFields() = println(
    """
      |Here are all the searchable fields;
    """.stripMargin
  )

  def enterSearchTerm() = println("enter search term")

  def enterSearchValue() = println("enter search value")

  def invalidInput(v: String) = println(s"invalid input ${v}, please enter again")

  def empty(): Unit = {}

  while (true) {
    console(0, openning)
  }

  @tailrec
  def console(stage: Int, printContext: () => Unit): Unit = {
    printContext()

    val info = scala.io.StdIn.readLine()

    many(letter).map(_.mkString).parse(info).done.map(command => {
      if (command == "quit") sys.exit(0)
    })

    stage match {
      case 0 =>
        println("yoyo")
        many(letter).map(_.mkString).parse(info).done match {
          case Done("", "") => console(1, moreInfo)
          case _ => console(0, openning)
        }
      case 1 =>
        int.parse(info).done match {
          case Done(_, 1) =>
            searchInstruction()
            console(2, empty)
          case Done(_, 2) =>
            searchableFields()
            console(1, moreInfo)
          case _ =>
            invalidInput(info)
            console(1, moreInfo)
        }
      case 2 =>
        int.parse(info).done match {
          case Done(_, 1) =>
            console(4, enterSearchTerm)
          case Done(_, 2) =>
            console(4, enterSearchTerm)
          case Done(_, 3) =>
            console(4, enterSearchTerm)
          case _ =>
            invalidInput(info)
            console(1, openning)
        }
      case 4 =>
        many(letter).map(_.mkString).parse(info).done match {
          case Done("", r: String) =>
            println(r)
            console(5, enterSearchValue)
          case _ =>
            invalidInput(info)
            console(4, enterSearchTerm)
        }
      case 5 =>
        many(letter).map(_.mkString).parse(info).done match {
          case Done("", r: String) =>
            println(r)
            console(4, enterSearchTerm)
          case _ =>
            int.parse(info).done match {
              case Done("", r: Int) =>
                println("done")
                println(r)
                console(4, enterSearchTerm)
              case _ =>
                invalidInput(info)
                console(5, enterSearchValue)
          }
        }
    }
  }

}
