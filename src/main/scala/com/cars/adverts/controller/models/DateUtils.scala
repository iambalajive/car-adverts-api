package com.cars.adverts.controller.models

import java.sql.Date
import java.text.{ParseException, SimpleDateFormat}
import java.util

object DateUtils {
  val dateFormat = new SimpleDateFormat("dd/MM/yyyy")

  def toUtilDate(date: String) = {
    try {
      Some(dateFormat.parse(date))
    }catch  {
      case _: ParseException => None
    }
  }

  def toSqlDate(date: String) = {
    toUtilDate(date).map(x => new Date(x.getTime))
  }

  def toString(date :util.Date) = {
    dateFormat.format(date)
  }
}
