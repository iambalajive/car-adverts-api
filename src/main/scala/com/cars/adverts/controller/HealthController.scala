package com.cars.adverts.controller

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import javax.inject.Singleton
import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck

@Singleton
class HealthController extends NamedHealthCheck {
  override def check(): HealthCheck.Result = Result.healthy()

  override def getName: String = "car-adverts-api"
}
