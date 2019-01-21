package com.cars.adverts.controller

import java.util.UUID

import com.cars.adverts.controller.models.{Advertisement, BadRequest, NotFound}
import com.cars.adverts.services.AdvertsService
import javax.annotation.Resource
import javax.inject.{Inject, Named}
import javax.validation.constraints.NotNull
import javax.ws.rs._
import javax.ws.rs.container.{AsyncResponse, Suspended}
import javax.ws.rs.core.Response.Status
import javax.ws.rs.core.{MediaType, Response}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/**
  * Adverts controller handles crud for advertisement
  * @param advertsService
  * @param executionContext
  */
@Resource
@Path("/advertisement")
@Produces(Array(MediaType.APPLICATION_JSON))
class AdvertsController @Inject()(advertsService : AdvertsService)
                                 (implicit @Named("executionContext") executionContext: ExecutionContext) {


  val logger = LoggerFactory.getLogger(classOf[AdvertsController])

  @GET
  @Path("/{id}")
  def get(@PathParam("id") id : UUID, @Suspended asyncResponse: AsyncResponse): Unit = {

    advertsService.get(id).onComplete {
      case Success(value) => value match  {
        case Some(carAdvert) => asyncResponse.resume(Response.ok().entity(carAdvert).build())
        case None  => asyncResponse.resume(Response.status(Status.NOT_FOUND).entity(NotFound.build).build())
      }
      case Failure(exception) => {
        logger.error(exception.getMessage)
        asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
      }
    }
  }


  // List all the adverts . Sort key and sort orders is non mandatory
  // Default sort key is id and sort order is descending

  @GET
  def list(@QueryParam("sortKey") sortKey : Option[String], @QueryParam("sortOrder") sortOrder: Option[String],
           @Suspended asyncResponse: AsyncResponse) = {

    advertsService.getAll(sortKey,sortOrder).onComplete {
      case Success(adverts) =>  asyncResponse.resume(Response.ok(adverts).build())

      case Failure(exception) => {
        logger.error(exception.getMessage)
        asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
      }
    }
  }


  @PUT
  def add(@NotNull  advertisement: Advertisement, @Suspended asyncResponse: AsyncResponse) = {

    if(Advertisement.isValid(advertisement).isSuccess) {
      advertsService.upsert(advertisement).onComplete {
        case Success(mayBeAdvert) => {
          mayBeAdvert match {
            case Some(advert) => asyncResponse.resume(Response.ok().entity(advert).build())
            case None => {
              asyncResponse.resume(Response.status(Status.BAD_REQUEST).entity(BadRequest.build).build())
            }
          }
        }
        case Failure(exception) => {
          logger.error(exception.getMessage)
          asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
        }
      }
    }else{
      asyncResponse.resume(Response.status(Status.BAD_REQUEST).entity(BadRequest.build).build())
    }

  }

  @DELETE
  @Path("/{id}")
  def delete(@PathParam("id") id:UUID, @Suspended asyncResponse: AsyncResponse) = {
    advertsService.delete(id).onComplete {
      case Success(result) => result match {
        case 0 => asyncResponse.resume(Response.status(Status.NOT_FOUND).entity(NotFound.build).build())
        case _ => asyncResponse.resume(Response.ok().entity(models.Success.build).build())
      }
      case Failure(exception) => {
        logger.error(exception.getMessage)
        asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
      }
    }
  }


  @POST
  @Path("/{id}")
  def update(@PathParam("id") id :UUID, @NotNull advertisement: Advertisement, @Suspended asyncResponse: AsyncResponse) = {

    if(advertisement.id.isDefined && Advertisement.isValid(advertisement).isSuccess) {
    advertsService.upsert(advertisement).onComplete {
      case Success(mayBeAdvert) => {
        mayBeAdvert match {
          case Some(advert) => asyncResponse.resume(Response.ok().entity(advert).build())
          case None =>   asyncResponse.resume(Response.status(Status.BAD_REQUEST).entity(BadRequest.build).build())
        }

      }
      case Failure(exception) => {
        logger.error(exception.getMessage)
        asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build())
      }
    }
  } else{
      asyncResponse.resume(Response.status(Status.BAD_REQUEST).entity(BadRequest.build).build())
    }
  }
}
