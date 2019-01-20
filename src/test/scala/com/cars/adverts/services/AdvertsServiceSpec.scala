package com.cars.adverts.services


import java.util.UUID

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


  after {
    reset(mockCarAdvertRepository)
    reset(mockVehicleConditionRepository)
    reset(mockFuelTypeRepository)
  }
}
