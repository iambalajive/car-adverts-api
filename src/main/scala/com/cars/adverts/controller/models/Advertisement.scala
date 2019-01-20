package com.cars.adverts.controller.models

import java.util.{Date, UUID}

import com.cars.adverts.dao.{CarAdvertEntity, FuelTypeEntity, VehicleConditionEntity}


case class Advertisement(title:String, id:Option[UUID], fuelType:String, price:Int,condition:String, mileage:Option[Int], firstReg:Option[String])

object Advertisement {

  def fromEntities(carAdvertEntity: CarAdvertEntity, fuelTypeEntity: FuelTypeEntity, vehicleConditionEntity: VehicleConditionEntity) = {
    Advertisement(carAdvertEntity.title,
      carAdvertEntity.id,
      fuelTypeEntity.fuelTypeDesc,
      carAdvertEntity.price,
      vehicleConditionEntity.condition,
      carAdvertEntity.mileage,
      carAdvertEntity.firstReg.map(DateUtils.toString)
    )
  }

  def toEntity(advertisement: Advertisement,fuelTypeEntity: FuelTypeEntity,vehicleConditionEntity: VehicleConditionEntity) = {
    CarAdvertEntity(
      advertisement.id,
      fuelTypeEntity.id,
      advertisement.title,
      advertisement.price,
      vehicleConditionEntity.id,
      advertisement.mileage,
      advertisement.firstReg.flatMap(DateUtils.toSqlDate)
    )
  }
}
