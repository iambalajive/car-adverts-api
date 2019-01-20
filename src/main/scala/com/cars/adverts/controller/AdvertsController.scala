package com.cars.adverts.controller

import java.util.UUID

import com.cars.adverts.controller.models.{Advertisement, NotFound}
import com.cars.adverts.dao.CarAdvertsRepository
import com.cars.adverts.services.AdvertsService
import javax.annotation.Resource
import javax.inject.{Inject, Named}
import javax.validation.constraints.NotNull
import javax.ws.rs._
import javax.ws.rs.container.{AsyncResponse, Suspended}
import javax.ws.rs.core.Response.Status
import javax.ws.rs.core.{MediaType, Response}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


@Resource
@Path("/advertisement")
@Produces(Array(MediaType.APPLICATION_JSON))
class AdvertsController @Inject()(carAdvertsRepository: CarAdvertsRepository, advertsService : AdvertsService)
                                 (implicit @Named("executionContext") executionContext: ExecutionContext) {

  @GET
  @Path("/{id}")
  def get(@PathParam("id") id : UUID, @Suspended asyncResponse: AsyncResponse): Unit = {

    carAdvertsRepository.getById(id).onComplete {
      case Success(value) => value match  {
        case Some(carAdvert) => asyncResponse.resume(Response.ok(carAdvert).build())
        case None  => asyncResponse.resume(Response.status(Status.NOT_FOUND).entity(NotFound("Not found")).build())
      }
      case Failure(exception) => {
        asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
      }
    }
  }

  @GET
  def list(@Suspended asyncResponse: AsyncResponse) = {
//    carAdvertsRepository.getAll(_,_).onComplete {
//      case Success(value) => value match  {
//        case carAdverts: List[CarAdvert] => asyncResponse.resume(Response.ok(carAdverts).build())
//        case _  => asyncResponse.resume(Response.status(Status.NOT_FOUND).build())
//      }
//      case Failure(exception) => asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
//    }
  }


  @PUT
  @Consumes(Array(MediaType.APPLICATION_JSON))
  def add(@NotNull  advertisement: Advertisement, @Suspended asyncResponse: AsyncResponse) = {

    advertsService.upsert(advertisement).onComplete {
      case Success(x) => asyncResponse.resume(Response.ok(x).build())
      case Failure(x) => asyncResponse.resume(Response.ok(x).build())
    }

  }

  @DELETE
  @Path("/{id}")
  def delete(@PathParam("id") id:UUID, @Suspended asyncResponse: AsyncResponse) = {
    carAdvertsRepository.delete(id).onComplete {
      case Success(result) => result match {
        case 0 => asyncResponse.resume(Response.status(Status.NOT_FOUND).entity(NotFound("Not found")).build())
        case 1 => asyncResponse.resume(Response.ok().entity().build())
      }
      case Failure(exception) => asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
    }
  }


  @POST
  @Path("/{id}")
  def update(@PathParam("id") id :UUID, @Suspended asyncResponse: AsyncResponse) = {

  }
}
