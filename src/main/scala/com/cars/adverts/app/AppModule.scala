package com.cars.adverts.app

import com.cars.adverts.configuration.AppConfiguration
import com.cars.adverts.dao.{DBComponent, PostGresDBComponent}
import com.google.inject.{AbstractModule, Provides}
import javax.inject.Singleton

class AppModule extends AbstractModule {



  @Provides
  def getDataBaseConfig(configuration: AppConfiguration) = configuration.database

  override def configure(): Unit = {
    bind(classOf[DBComponent]).to(classOf[PostGresDBComponent]).in(classOf[Singleton])
  }
}
