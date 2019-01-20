package com.cars.adverts.controller.models

object ValidFuelTypes extends Enumeration {
  type ValidFuelTypes = Value

  val PETROL = Value("PETROL")
  val DIESEL = Value("DIESEL")
}


object ValidConditionTypes extends Enumeration {
  type ValidConditionTypes = Value

  val USED = Value("USED")
  val NEW = Value("NEW")
}