package com.cars.adverts.app

import java.util.concurrent.Executors

import com.cars.adverts.configuration.AppConfiguration
import com.cars.adverts.dao.{DBComponent, PostGresDBComponent}
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Provides}
import javax.inject.Singleton

import scala.concurrent.ExecutionContext

class AppModule extends AbstractModule {



  @Provides
  def getDataBaseConfig(configuration: AppConfiguration) = configuration.database

  override def configure(): Unit = {
    bind(classOf[DBComponent]).to(classOf[PostGresDBComponent]).in(classOf[Singleton])

    bind(classOf[ExecutionContext]).annotatedWith(Names.named("executionContext"))
      .toInstance(ExecutionContext.fromExecutor(Executors.newFixedThreadPool(100)))


  }
}
