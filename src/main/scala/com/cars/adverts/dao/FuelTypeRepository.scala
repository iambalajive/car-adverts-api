package com.cars.adverts.dao

import javax.inject.{Inject, Singleton}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future


@Singleton
class FuelTypeRepository @Inject() (dbComponent: DBComponent) extends FuelTypeTable {


  override val driver = dbComponent.driver

   import driver.api._

   private val db: Database = dbComponent.db

   def create(fuelType: FuelTypeEntity):Future[Int] = db.run { fuelTypes += fuelType }

   def getByFuelTypeDesc(fuelTypeDesc:String) = db.run{ fuelTypes.filter(_.fuelTypeDesc === fuelTypeDesc).result.headOption}

   def getById(id: Int): Future[Option[FuelTypeEntity]] = db.run { fuelTypes.filter(_.id === id).result.headOption }

   def getAll(): Future[List[FuelTypeEntity]] = db.run { fuelTypes.to[List].result }

   def delete(id: Int): Future[Int] = db.run { fuelTypes.filter(_.id === id).delete }

}

private [dao] trait FuelTypeTable {

  protected val driver:JdbcProfile
  import driver.api._

  private[FuelTypeTable] class FuelTypes(tag: Tag) extends Table[FuelTypeEntity](tag,"FUEL_TYPE") {
    val id = column[Int]("ID", O.PrimaryKey)
    val fuelTypeDesc = column[String]("FUEL_TYPE_DESC")
    def * = (fuelTypeDesc,id) <> (FuelTypeEntity.tupled, FuelTypeEntity.unapply)
  }


  protected val fuelTypes = TableQuery[FuelTypes]

}

case class FuelTypeEntity(fuelTypeDesc: String, id: Int)