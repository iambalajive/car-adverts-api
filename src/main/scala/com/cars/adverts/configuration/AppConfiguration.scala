package com.cars.adverts.configuration

import io.dropwizard.Configuration

class AppConfiguration(val database: DatabaseConfig) extends Configuration

class DatabaseConfig(val driverClass:String,val username:String,val password:String, val url: String, val name: String, val port:Int){
  val jdbcUrl = s"jdbc:postgresql://$url:$port/$name?user=$username&password=$password"
}
