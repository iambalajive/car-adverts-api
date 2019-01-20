package com.cars.adverts.dao

import java.util.UUID

import javax.inject.{Inject, Named, Singleton}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CarAdvertsRepository @Inject() (dbComponent: DBComponent) (implicit @Named("executionContext") executionContext: ExecutionContext) extends CarAdvertsTable {

  override val driver = dbComponent.driver
  import driver.api._

  private val db: Database = dbComponent.db

  def create(carAdvert: CarAdvertEntity):Future[CarAdvertEntity] = {
    val id = UUID.randomUUID()
    val toBeInserted = carAdvert.copy(id = Some(id))
    db.run { carAdverts += toBeInserted }.map(_ => toBeInserted )
  }

  def getById(id: UUID): Future[Option[CarAdvertEntity]] = db.run { carAdverts.filter(_.id === id).result.headOption }

  def getAll(sortOrder:Int,sortKey:String): Future[List[CarAdvertEntity]] = {
    db.run { carAdverts.sortBy(_.id).to[List].result }
  }

  def getAllWithMeta(sortKey : Option[String] = None, sortOrder: Option[Int] = None):Future[List[((CarAdvertEntity,FuelTypeEntity),VehicleConditionEntity)]]= {

    val query = carAdverts.join(fuelTypes)
      .on((carAdvert,fuelType) => carAdvert.fuelTypeId === fuelType.id)
        .join(vehicleConditions).on((x,y) => x._1.conditionType=== y.id)
//        .sortBy(_._1._1.mileage.asc)

    db.run {
      query.to[List].result
    }
  }

  def delete(id: UUID): Future[Int] = db.run { carAdverts.filter(_.id === id).delete }

  def update(carAdvert: CarAdvertEntity): Future[CarAdvertEntity] = db.run { carAdverts.filter(_.id === carAdvert.id).update(carAdvert).map(_ => carAdvert) }

}


private [dao] trait CarAdvertsTable extends FuelTypeTable  with VehicleConditionTable  {

  protected val driver:JdbcProfile

  import driver.api._

  private[CarAdvertsTable] class CarAdverts(tag: Tag) extends Table[CarAdvertEntity](tag,"CAR_ADVERTS")  {
    val id = column[UUID]("ID", O.PrimaryKey)
    val fuelTypeId = column[Int]("FUEL_TYPE_ID")
    val title = column[String]("TITLE")
    val price = column[Int]("PRICE")
    val conditionType = column[Int]("CONDITION_TYPE_ID")
    val mileage = column[String]("MILEAGE")
    val firstReg = column[java.sql.Date]("FIRST_REG")
    def * = (id.?,fuelTypeId,title,price,conditionType,mileage.?,firstReg.?) <> (CarAdvertEntity.tupled, CarAdvertEntity.unapply)

  }

  protected val carAdverts = TableQuery[CarAdverts]
}



case class CarAdvertEntity(id: Option[UUID]= None, fuelTypeId:Int, title:String, price:Int,
                           conditionType:Int, mileage:Option[String] = None, firstReg:Option[java.sql.Date] = None)