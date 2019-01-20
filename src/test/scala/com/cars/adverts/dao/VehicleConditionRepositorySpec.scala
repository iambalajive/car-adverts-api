package com.cars.adverts.dao

import org.scalatest.{BeforeAndAfter, FlatSpec}

class VehicleConditionRepositorySpec  extends FlatSpec with BeforeAndAfter with DAOSpec {

  var vehicleConditionRepository:VehicleConditionRepository = _
  var dbComponent:DBComponent = _
  before {
    dbComponent = createSchema()
    vehicleConditionRepository = new VehicleConditionRepository(dbComponent)
  }


  it should "List all the available types i.e OLD and NEW" in {
    val list = vehicleConditionRepository.getAll().futureValue

    assert(list.length === 2 )
  }

  it should "be able to insert a new vehicle type if required " in {

    val vehicleCondition = VehicleConditionEntity(3,"SEMI_NEW")
    val added = vehicleConditionRepository.create(vehicleCondition).futureValue
    assert(added === 1 )
  }

  it should "be able to delete a vehicle type by id " in {

    val deleted = vehicleConditionRepository.delete(1).futureValue
    assert(deleted === 1 )
  }

  it should "be able to find by condition type desc" in {

    val deleted = vehicleConditionRepository.getByCondition("USED").futureValue
    assert(deleted.get.id === 2 )
  }
  after {
    tearDown(dbComponent)
  }
}
