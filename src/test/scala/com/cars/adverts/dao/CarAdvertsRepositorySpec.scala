package com.cars.adverts.dao

import java.sql.Date

import com.cars.adverts.controller.models.DateUtils
import org.scalatest.{BeforeAndAfter, FlatSpec}

import scala.concurrent.ExecutionContext.Implicits.global


class CarAdvertsRepositorySpec  extends FlatSpec with BeforeAndAfter with DAOSpec {

  var carAdvertsRepository:CarAdvertsRepository = _
  var dbComponent:DBComponent = _

  before {
    dbComponent = createSchema()
    carAdvertsRepository = new CarAdvertsRepository(dbComponent)
  }

  it should "be able to insert a new car advert  " in {

    val carAdvert = CarAdvertEntity(None,1,"My Listing",14,1)

    val added = carAdvertsRepository.create(carAdvert).futureValue
  }


  it should "be able to delete an advert by id " in {

    val carAdvert = CarAdvertEntity(None,1,"My Listing",14,1)

    val added = carAdvertsRepository.create(carAdvert).futureValue

    val deleted = carAdvertsRepository.delete(added.id.get).futureValue

    assert(deleted == 1)
  }

  it should "be able to list all the car adverts" in {

    val carAdvert1 = CarAdvertEntity(None,1,"My Listing",14,1)
    val carAdvert2 = CarAdvertEntity(None,1,"My Listing 2",14,1)

    carAdvertsRepository.create(carAdvert1).futureValue
    carAdvertsRepository.create(carAdvert2).futureValue

    val list = carAdvertsRepository.getAllWithMeta().futureValue
    assert(list.length == 2)
  }


  it should "be able to update a car advert" in {

    val carAdvert1 = CarAdvertEntity(None,1,"My Listing",14,1)

    val added = carAdvertsRepository.create(carAdvert1).futureValue

    val modifiedTitle = "Modified"

    val update = added.copy(title = modifiedTitle)

    carAdvertsRepository.update(update).futureValue

    val carAdvert = carAdvertsRepository.getById(added.id.get).futureValue

    assert(carAdvert.get.title == modifiedTitle)
  }

  it should "be able to get advertisement with meta by id" in {

    val carAdvert1 = CarAdvertEntity(None,1,"My Listing",14,1)

    val added = carAdvertsRepository.create(carAdvert1).futureValue

    val carAdvert = carAdvertsRepository.getWithMetaById(added.id.get).futureValue

    assert(carAdvert.get._1._1.id === added.id)
  }


  it should "be able to create an entity with reg date" in {

    val carAdvert1 = CarAdvertEntity(None,1,"My Listing",14,1,Some(132),DateUtils.toSqlDate("11/11/2001"))

    val added = carAdvertsRepository.create(carAdvert1).futureValue

    val carAdvert = carAdvertsRepository.getWithMetaById(added.id.get).futureValue

    assert(carAdvert.get._1._1.id === added.id)
    assert(carAdvert.get._1._1.firstReg.isDefined)
  }


  after {
    tearDown(dbComponent)
  }
}


