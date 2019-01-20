package com.cars.adverts.configuration

import io.dropwizard.Configuration

case class AppConfiguration(database: DatabaseConfig,flywayMigration :Boolean) extends Configuration

case class DatabaseConfig(driverClass:String,username:String,password:String, url: String, name: String, val port:Int){
  val jdbcUrl = s"jdbc:postgresql://$url:$port/$name?user=$username&password=$password"
}
