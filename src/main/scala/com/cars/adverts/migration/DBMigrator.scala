package com.cars.adverts.migration

import org.flywaydb.core.Flyway


class DBMigrator(val connectionUrl:String, val username:String, val password:String) {

   private val flyway = Flyway.configure().dataSource(this.connectionUrl, this.username, this.password).load()

   def migrate = flyway.migrate()

}