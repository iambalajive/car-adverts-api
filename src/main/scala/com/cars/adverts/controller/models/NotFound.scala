package com.cars.adverts.controller.models

case class NotFound(msg : String) {
  def this() = this("Entity not found")
}

