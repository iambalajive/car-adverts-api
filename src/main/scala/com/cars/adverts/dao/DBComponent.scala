package com.cars.adverts.dao

import com.cars.adverts.configuration.DatabaseConfig
import javax.inject.{Inject, Singleton}
import slick.jdbc.JdbcProfile


trait DBComponent {

  val driver: JdbcProfile

  import driver.api._

  val db: Database

}


@Singleton
class PostGresDBComponent @Inject() (config : DatabaseConfig) extends DBComponent {
  val driver = slick.jdbc.PostgresProfile
  import driver.api._
  val db = Database.forURL(
    config.jdbcUrl,
    driver =config.driverClass)
}


class H2DBComponent(jdbcUrl : String) extends DBComponent {
  val driver = slick.jdbc.H2Profile
  import driver.api._

  val db =Database.forURL(jdbcUrl, driver = "org.h2.Driver")

}