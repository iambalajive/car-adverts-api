package com.cars.adverts.controller.models

case class Message(msg : String)

object NotFound  {
  def build = Message("The given entity is not found")
}


object Success {
  def build = Message("The given operation was a success")
}


object BadRequest {
  def build = Message("The request is invalid")
}
