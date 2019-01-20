package com.cars.adverts.controller.models

import java.util.{Date, UUID}

import com.cars.adverts.dao.{CarAdvertEntity, FuelTypeEntity, VehicleConditionEntity}


case class Advertisement(title:String, id:Option[UUID], fuelType:String, price:Int,condition:String, mileage:Option[String], firstReg:Option[Date])

object Advertisement {

  def toAdvertisement(carAdvertEntity: CarAdvertEntity,fuelTypeEntity: FuelTypeEntity, vehicleConditionEntity: VehicleConditionEntity) = {
    Advertisement(carAdvertEntity.title,
      carAdvertEntity.id,
      fuelTypeEntity.fuelTypeDesc,
      carAdvertEntity.price,
      vehicleConditionEntity.condition,
      carAdvertEntity.mileage,
      carAdvertEntity.firstReg.map(date => new Date(date.getTime))
    )
  }
}
