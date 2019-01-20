package com.cars.adverts.dao

import java.util.UUID

import com.cars.adverts.migration.DBMigrator
import org.scalatest.concurrent.ScalaFutures


trait DAOSpec extends ScalaFutures {



  def createSchema(id:UUID = UUID.randomUUID()) = {
     val h2jdbcUrl = s"jdbc:h2:mem:${id.toString};DB_CLOSE_DELAY=-1;database_to_upper=false"
     new DBMigrator(connectionUrl = h2jdbcUrl,username = "",password = "").migrate
     new H2DBComponent(h2jdbcUrl)
  }


  def tearDown(dbComponent: DBComponent) = {
    import dbComponent.driver.api._
    val value = dbComponent.db.run(DBIO.seq(sqlu"DROP ALL OBJECTS")).futureValue
  }
}
