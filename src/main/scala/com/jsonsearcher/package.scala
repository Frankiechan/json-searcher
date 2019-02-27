package com

import com.jsonsearcher.models.{Organization, Ticket, User, View}

package object jsonsearcher {
  type Resources = Tuple3[List[User], List[Ticket], List[Organization]]
  type Index[A] = Map[A, List[Int]]
  type IndexDictionary[A] = Map[String, Index[A]]
  type SearchResults = List[View]
}
