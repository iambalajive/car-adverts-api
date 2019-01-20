package com.cars.adverts.dao

import org.scalatest.{BeforeAndAfter, FlatSpec}

class FuelTypeRepositorySpec  extends FlatSpec with BeforeAndAfter with DAOSpec {

  var fuelTypeRepository:FuelTypeRepository = _
  var dbComponent:DBComponent = _
  before {
    dbComponent = createSchema()
    fuelTypeRepository = new FuelTypeRepository(dbComponent)
  }


  it should "List all the available types i.e petrol and diesel" in {
    val list = fuelTypeRepository.getAll().futureValue

    assert(list.length === 2 )
  }

  it should "be able to insert a new fuel type if required " in {

    val fuelType = FuelType("ELECTRONIC",3)
    val added = fuelTypeRepository.create(fuelType).futureValue
    assert(added === 1 )
  }

  it should "be able to delete a fuel type by id " in {

    val deleted = fuelTypeRepository.delete(1).futureValue
    assert(deleted === 1 )
  }

  it should "be able to find by fuel type desc" in {

    val deleted = fuelTypeRepository.getByFuelTypeDesc("DIESEL").futureValue
    assert(deleted.get.id === 2 )
  }
  after {
    tearDown(dbComponent)
  }
}
