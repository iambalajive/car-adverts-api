package com.cars.adverts.services

import java.util.UUID

import com.cars.adverts.controller.models.{Advertisement, DateUtils}
import com.cars.adverts.dao.{CarAdvertEntity, FuelTypeEntity, VehicleConditionEntity}

trait DataStub {


  def getOneEntityById(id:UUID) = {
    val carAdvert = CarAdvertEntity(Some(id),1,"Title",10,1,Some(1),DateUtils.toSqlDate("11/11/2001"))
    val vehicleConditionType = VehicleConditionEntity(1,"USED")
    val fuelType = FuelTypeEntity("PETROL",1)

    ((carAdvert,fuelType),vehicleConditionType)
  }


  def getManyEntities(count :Int = 5) = {
    (1 to 5).map(
      _ => getOneEntityById(UUID.randomUUID())
    ).toList
  }


}
