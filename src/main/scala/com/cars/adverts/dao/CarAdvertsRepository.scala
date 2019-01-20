package com.cars.adverts.dao

import java.util.UUID

import javax.inject.{Inject, Named, Singleton}
import slick.jdbc.JdbcProfile
import slick.lifted.{ColumnOrdered, Rep}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CarAdvertsRepository @Inject() (dbComponent: DBComponent)
                                     (implicit @Named("executionContext") executionContext: ExecutionContext) extends CarAdvertsTable {

  override val driver = dbComponent.driver
  import driver.api._

  private val db: Database = dbComponent.db

  private val joinQuery = carAdverts.join(fuelTypes)
    .on((carAdvert,fuelType) => carAdvert.fuelTypeId === fuelType.id)
    .join(vehicleConditions).on((x,y) => x._1.conditionType=== y.id)


  def create(carAdvert: CarAdvertEntity):Future[CarAdvertEntity] = {
    val id = UUID.randomUUID()
    val toBeInserted = carAdvert.copy(id = Some(id))
    db.run { carAdverts += toBeInserted }.map(_ => toBeInserted )
  }

  def getById(id: UUID): Future[Option[CarAdvertEntity]] = db.run { carAdverts.filter(_.id === id).result.headOption }

  def getAll(sortOrder:Int,sortKey:String): Future[List[CarAdvertEntity]] = {
    db.run { carAdverts.sortBy(_.id).to[List].result }
  }


  def getWithMetaById(id : UUID):Future[Option[((CarAdvertEntity,FuelTypeEntity),VehicleConditionEntity)]] = {
    db.run {
      joinQuery.filter(_._1._1.id === id).result.headOption
    }
  }


  def getAllWithMeta(sortKey : Option[String] = None, sortOrder: Option[String] = None) :Future[List[((CarAdvertEntity,FuelTypeEntity),VehicleConditionEntity)]]= {

    val sortKeyParam = sortKey.getOrElse("id")
    val sortOrderParam = sortOrder.getOrElse("desc")


   val queryWithSort = joinQuery.sortBy{
      sortKeyParam match {
        case "id" => if(sortOrderParam == "desc") _._1._1.id.desc else _._1._1.id.asc
        case "condition" => if(sortOrderParam == "desc") _._2.condition.desc  else _._2.condition.asc
        case "title" => if(sortOrderParam == "desc") _._1._1.title.desc  else _._1._1.title.asc
        case "price" => if(sortOrderParam == "desc") _._1._1.price.desc  else _._1._1.price.asc
        case "mileage" => if(sortOrderParam == "desc") _._1._1.mileage.desc  else _._1._1.mileage.asc
        case "firstReg" => if(sortOrderParam == "desc") _._1._1.firstReg.desc  else _._1._1.firstReg.asc
        case "fuelType" => if(sortOrderParam == "desc") _._1._2.fuelTypeDesc.desc  else _._1._2.fuelTypeDesc.asc
      }
    }

    db.run {
      queryWithSort.to[List].result
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
    val mileage = column[Int]("MILEAGE")
    val firstReg = column[java.sql.Date]("FIRST_REG")
    def * = (id.?,fuelTypeId,title,price,conditionType,mileage.?,firstReg.?) <> (CarAdvertEntity.tupled, CarAdvertEntity.unapply)

  }

  protected val carAdverts = TableQuery[CarAdverts]
}

trait Selector { def sortFields: Map[String, Rep[_]] }





case class CarAdvertEntity(id: Option[UUID]= None, fuelTypeId:Int, title:String, price:Int,
                           conditionType:Int, mileage:Option[Int] = None, firstReg:Option[java.sql.Date] = None)
