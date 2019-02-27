package com.jsonsearcher.core

import com.jsonsearcher.IndexDictionary

case class IndexDictionaries(longIndexDictionary: IndexDictionary[Long],
                             stringIndexDictionary: IndexDictionary[String],
                             boolIndexDictionary: IndexDictionary[Boolean])

//object ServeIndexDict {
//  def apply[F[_], T](searchTerm: SearchTerm, dicts: IndexDictionaries)(implicit F: MonadError[F, Throwable], tag: ClassTag[T]): F[IndexDictionary[T]] = {
//    import scala.reflect._
//
//    if (tag == classTag[Long]) F.pure(dicts.longIndexDictionary.asInstanceOf[IndexDictionary[T]])
//    else if (tag == classTag[String]) F.pure(dicts.stringIndexDictionary.asInstanceOf[IndexDictionary[T]])
//    else if (tag == classTag[Boolean]) F.pure(dicts.boolIndexDictionary.asInstanceOf[IndexDictionary[T]])
//    else F.raiseError(NoSuchDictionaryTypeException(s"${tag.toString()} is not a support type in index dictionary"))
//  }
//}