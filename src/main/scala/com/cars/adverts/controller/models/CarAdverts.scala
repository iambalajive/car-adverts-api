package com.cars.adverts.controller.models

import java.util.{Date, UUID}


case class Advertisement(title:String, id:Option[UUID], fuelType:String, price:Int, mileage:Option[String], firstReg:Option[Date], condition:String)
