package com.cars.adverts.services

import com.cars.adverts.controller.models.Advertisement
import com.cars.adverts.dao.{CarAdvertEntity, CarAdvertsRepository, FuelTypeRepository, VehicleConditionRepository}
import javax.inject.{Inject, Named, Singleton}

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class AdvertsService @Inject()(carAdvertsRepository: CarAdvertsRepository,
                               fuelTypeRepository: FuelTypeRepository,
                               vehicleConditionRepository: VehicleConditionRepository)(implicit @Named("executionContext") executionContext: ExecutionContext) {


   private def createOrUpdate(carAdvert: CarAdvertEntity) = {
      val id  = carAdvert.id
      id match {
         case Some(_) => carAdvertsRepository.update(carAdvert)
         case None => carAdvertsRepository.create(carAdvert)
      }
   }

   def upsert(advertisement: Advertisement) = {
      fuelTypeRepository.getByFuelTypeDesc(advertisement.fuelType).flatMap {
         case Some(fuelType) => vehicleConditionRepository.getByCondition(advertisement.condition).flatMap {
            case Some(vehicleCondition) => {
               val carAdvert = CarAdvertEntity(advertisement.id,fuelType.id,advertisement.title,advertisement.price,vehicleCondition.id,null,null)
               createOrUpdate(carAdvert).map(x => "success")
            }
            case None  => Future.successful("failure")
         }
         case None => Future.successful("missing")
      }.recoverWith {
         case _: IllegalStateException => Future.successful("")
      }
   }
}
