package com

import com.jsonsearcher.models.{Organization, Ticket, User}

package object jsonsearcher {
  type Resources = Tuple3[List[User], List[Ticket], List[Organization]]
  type ErrorOr[A] = Either[AppError, A]
}
