package com.cars.adverts.services

import java.util.UUID

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

   def get(id :UUID) = {
      carAdvertsRepository.getWithMetaById(id).map {
         case Some(tuple) => Some(Advertisement.fromEntities(tuple._1._1,tuple._1._2,tuple._2))
         case _ => None
      }
   }

   def getAll(sortKey:Option[String] = None, sortOrder :Option[Int]) = {
      carAdvertsRepository.getAllWithMeta(sortKey,sortOrder).map {
         adverts => adverts.map(advert => Advertisement.fromEntities(advert._1._1,advert._1._2,advert._2))
      }
   }

   def delete(id: UUID) = {
      carAdvertsRepository.delete(id)
   }

   def update(id: UUID,advertisement: Advertisement) = {
      upsert(advertisement.copy(id = Some(id)))
   }


   def upsert(advertisement: Advertisement) = {
      fuelTypeRepository.getByFuelTypeDesc(advertisement.fuelType).flatMap {
         case Some(fuelType) => vehicleConditionRepository.getByCondition(advertisement.condition).flatMap {
            case Some(vehicleCondition) => {
               val carAdvert = Advertisement.toEntity(advertisement,fuelType,vehicleCondition)
               createOrUpdate(carAdvert).map(x => Some(Advertisement.fromEntities(x,fuelType,vehicleCondition)))
            }
            case None  => Future.successful(None)
         }
         case None => Future.successful(None)
      }
   }
}
