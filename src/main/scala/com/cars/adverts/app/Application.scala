package com.cars.adverts.app


import com.cars.adverts.configuration.AppConfiguration
import com.cars.adverts.migration.DBMigrator
import io.dropwizard.setup.{Bootstrap, Environment}
import ru.vyarus.dropwizard.guice.GuiceBundle
import com.datasift.dropwizard.scala.ScalaApplication


object Application extends ScalaApplication[AppConfiguration] {
  override def init(bootstrap: Bootstrap[AppConfiguration]): Unit = {
    bootstrap.addBundle(GuiceBundle.builder[AppConfiguration]().enableAutoConfig("com.cars.adverts").modules(new AppModule).build())

  }

  override def run(conf: AppConfiguration, env: Environment): Unit = {
    new DBMigrator(conf.database.jdbcUrl,conf.database.username,conf.database.password).migrate
  }
}

