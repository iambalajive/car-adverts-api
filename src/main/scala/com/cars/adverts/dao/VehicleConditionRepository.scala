package com.cars.adverts.dao

import javax.inject.{Inject, Singleton}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

@Singleton
class VehicleConditionRepository @Inject() (dbComponent: DBComponent) extends VehicleConditionTable {


  override val driver: JdbcProfile = dbComponent.driver

  import driver.api._

  private val db: Database = dbComponent.db

  def getByCondition(condition:String): Future[Option[VehicleConditionEntity]] = db.run { vehicleConditions.filter(_.condition === condition).result.headOption }

  def delete(id:Int):Future[Int] = db.run { vehicleConditions.filter(_.id === id).delete }

  def create(vehicleCondition: VehicleConditionEntity):Future[Int] = db.run { vehicleConditions += vehicleCondition }

  def getById(id: Int): Future[Option[VehicleConditionEntity]] = db.run { vehicleConditions.filter(_.id === id).result.headOption }

  def getAll(): Future[List[VehicleConditionEntity]] = db.run { vehicleConditions.to[List].result }

}


private [dao] trait VehicleConditionTable {

  protected val driver:JdbcProfile
  import driver.api._

  private[VehicleConditionTable] class VehicleConditions(tag: Tag) extends Table[VehicleConditionEntity](tag,"VEHICLE_CONDITION") {
    val id = column[Int]("ID", O.PrimaryKey)
    val condition = column[String]("CONDITION")
    def * = (id,condition) <> (VehicleConditionEntity.tupled, VehicleConditionEntity.unapply)
  }


  protected val vehicleConditions = TableQuery[VehicleConditions]

}


case class VehicleConditionEntity(id: Int, condition:String)