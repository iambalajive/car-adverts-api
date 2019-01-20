package com.cars.adverts.controller.models

import java.util.UUID

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


  // Wix validations
  import com.wix.accord.dsl._

  implicit val advertisementValidator = validator[Advertisement] { a =>
    a.title is notEmpty
    a.fuelType is notEmpty
    a.price should be > 0
    a.condition is notEmpty

    // valid fuel type
    a.fuelType is in (ValidFuelTypes.DIESEL.toString,ValidFuelTypes.PETROL.toString)

    // valid condition types
    a.condition is in ( ValidConditionTypes.NEW.toString, ValidConditionTypes.USED.toString)

    // if condition is USED then mileage and firstReg is required
    if(a.condition == "USED") {
      a.mileage.isDefined is true
      a.firstReg.isDefined is true

      // Check is the date format is valid
      if(a.firstReg.isDefined) {
        DateUtils.toUtilDate(a.firstReg.get).isDefined is true
      }
    }

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
