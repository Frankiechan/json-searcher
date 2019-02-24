package com.jsonsearcher

import java.nio.file.Paths

object ResourcesConfig {

  object DataStore {
    private val cwd = Paths.get(System.getProperty("user.dir"))
    private val resourcesDir = Paths.get(cwd.toString, "resources").toString

    val organizations = Paths.get(resourcesDir, "organizations.json")
    val tickets = Paths.get(resourcesDir, "tickets.json")
    val users = Paths.get(resourcesDir, "users.json")

  }

}
