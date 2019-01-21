package com.cars.adverts.app


import com.cars.adverts.configuration.AppConfiguration
import com.cars.adverts.migration.DBMigrator
import io.dropwizard.setup.{Bootstrap, Environment}
import ru.vyarus.dropwizard.guice.GuiceBundle
import com.datasift.dropwizard.scala.ScalaApplication
import org.eclipse.jetty.servlets.CrossOriginFilter
import javax.servlet.DispatcherType
import java.util


/**
  * Application bootstap
  */

object Application extends ScalaApplication[AppConfiguration] {
  override def init(bootstrap: Bootstrap[AppConfiguration]): Unit = {
    bootstrap.addBundle(GuiceBundle.builder[AppConfiguration]().enableAutoConfig("com.cars.adverts").modules(new AppModule).build())

  }

  override def run(conf: AppConfiguration, env: Environment): Unit = {

    if(conf.flywayMigration){
      new DBMigrator(conf.database.jdbcUrl,conf.database.username,conf.database.password).migrate

    }

    val cors = env.servlets().addFilter("CORS", classOf[CrossOriginFilter])

    //Generic CORS setup . there is auth required in the app
    // Configure CORS parameters
    cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*")
    cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization")
    cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD")
    cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true")

    // Add URL mapping
    cors.addMappingForUrlPatterns(util.EnumSet.allOf(classOf[DispatcherType]), true, "/*")


  }
}

