package com.jsonsearcher.core

import com.jsonsearcher.IndexDictionary

case class IndexDictionaries(longIndexDictionary: IndexDictionary[Long],
                             stringIndexDictionary: IndexDictionary[String],
                             boolIndexDictionary: IndexDictionary[Boolean])