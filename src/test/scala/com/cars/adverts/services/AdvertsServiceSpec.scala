package com.cars.adverts.services


import java.util.UUID

import com.cars.adverts.controller.models.{Advertisement, DateUtils}
import com.cars.adverts.dao.{CarAdvertEntity, CarAdvertsRepository, FuelTypeRepository, VehicleConditionRepository}
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class AdvertsServiceSpec extends FlatSpec with ScalaFutures with BeforeAndAfter with MockitoSugar with DataStub {

  val mockCarAdvertRepository = mock[CarAdvertsRepository]
  val mockFuelTypeRepository = mock[FuelTypeRepository]
  val mockVehicleConditionRepository = mock[VehicleConditionRepository]

  val advertsService = new AdvertsService(mockCarAdvertRepository, mockFuelTypeRepository, mockVehicleConditionRepository)


  it should "Get the given Car advert by id with details " in {

    val id = UUID.randomUUID()
    when(mockCarAdvertRepository.getWithMetaById(id)).thenReturn(Future.successful(Some(getOneEntityById(id))))
    val mayBeAdvertisement = advertsService.get(id).futureValue

    assert(mayBeAdvertisement.get.condition == "USED")
    assert(mayBeAdvertisement.get.fuelType == "PETROL")
    assert(mayBeAdvertisement.get.firstReg.isDefined)

  }


  it should "Return None if id is not found " in {

    val id = UUID.randomUUID()
    when(mockCarAdvertRepository.getWithMetaById(id)).thenReturn(Future.successful(None))
    val mayBeAdvertisement = advertsService.get(id).futureValue

    assert(mayBeAdvertisement.isEmpty)

  }

  it should "Delete a given car advert by id" in {

    val id = UUID.randomUUID()
    when(mockCarAdvertRepository.delete(id)).thenReturn(Future.successful(1))
    val deleted = advertsService.delete(id).futureValue

    assert(deleted == 1)

  }

  it should "Create an entity given a request model" in {

    val id = UUID.randomUUID()

    val carAdvertWithMeta = getOneEntityById(id)
    val advertisement = Advertisement.fromEntities(carAdvertWithMeta._1._1,carAdvertWithMeta._1._2,carAdvertWithMeta._2)

    when(mockCarAdvertRepository.create(carAdvertWithMeta._1._1.copy(id = None))).thenReturn(Future.successful(carAdvertWithMeta._1._1))
    when(mockFuelTypeRepository.getByFuelTypeDesc(carAdvertWithMeta._1._2.fuelTypeDesc)).thenReturn(Future.successful(Some(carAdvertWithMeta._1._2)))
    when(mockVehicleConditionRepository.getByCondition(carAdvertWithMeta._2.condition)).thenReturn(Future.successful(Some(carAdvertWithMeta._2)))

    val mayBeAdvertisement = advertsService.upsert(advertisement.copy(id = None)).futureValue

    assert(mayBeAdvertisement.isDefined)
    assert(mayBeAdvertisement.get.firstReg.get == "11/11/2001")
  }



  it should "Update the entity for the given id " in {

    val id = UUID.randomUUID()

    val modifiedTitle = "Modified title"
    val modifiedDate = "30/01/2011"
    val carAdvertWithMeta = getOneEntityById(id)

    val advertisement = Advertisement.fromEntities(carAdvertWithMeta._1._1,carAdvertWithMeta._1._2,carAdvertWithMeta._2)
      .copy(title = modifiedTitle,firstReg = Some(modifiedDate))

    val updatedCarAdvert = carAdvertWithMeta._1._1.copy(title = modifiedTitle, firstReg = DateUtils.toSqlDate(modifiedDate))

    when(mockCarAdvertRepository.update(updatedCarAdvert)).thenReturn(Future.successful(updatedCarAdvert))

    when(mockFuelTypeRepository.getByFuelTypeDesc(carAdvertWithMeta._1._2.fuelTypeDesc)).thenReturn(Future.successful(Some(carAdvertWithMeta._1._2)))
    when(mockVehicleConditionRepository.getByCondition(carAdvertWithMeta._2.condition)).thenReturn(Future.successful(Some(carAdvertWithMeta._2)))

    val mayBeAdvertisement = advertsService.upsert(advertisement).futureValue

    assert(mayBeAdvertisement.isDefined)
    assert(mayBeAdvertisement.get.firstReg.get == modifiedDate)
    assert(mayBeAdvertisement.get.title == modifiedTitle)
  }




  after {
    reset(mockCarAdvertRepository)
    reset(mockVehicleConditionRepository)
    reset(mockFuelTypeRepository)
  }
}
