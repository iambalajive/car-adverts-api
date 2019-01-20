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


object ValidSortOrders extends Enumeration {
  type ValidSortOrders = Value

  val ASC = Value("asc")
  val DESC = Value("desc")
}


object ValidSortKeys extends Enumeration {
  val ID = Value("id")

}