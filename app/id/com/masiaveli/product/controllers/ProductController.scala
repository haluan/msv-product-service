package id.com.masiaveli.product.controllers

import java.time.{Instant, ZoneId, ZonedDateTime}
import java.util.UUID

import id.com.masiaveli.product.controllers.requests.CreateProductRequest
import id.com.masiaveli.product.controllers.requests.RequestsFormatter._
import id.com.masiaveli.product.controllers.responses.ResponsesFormatter._
import id.com.masiaveli.product.controllers.responses.{CreateProductResponse, ListProductsResponse}
import id.com.masiaveli.product.model.ModelsFormatter._
import id.com.masiaveli.product.model.Product
import id.com.masiaveli.product.repositories.ProductRepo
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import reactivemongo.bson.BSONDateTime

import scala.concurrent.{ExecutionContext, Future}

class ProductController @Inject()(productRepo: ProductRepo)(implicit ec: ExecutionContext) extends Controller {
  def create = Action.async(parse.json){ request =>
    request.body.validate[CreateProductRequest] map { request =>
      request.validateTimeLength match {
        case false => Future.successful(BadRequest)
        case true =>
          val product = buildProduct(request)
          productRepo.create(product) map { _ =>
            Created(Json.toJson(CreateProductResponse(id = product.id, createdTime = product.createdTime)))
          }
      }
    } recoverTotal {
      case ex =>
        Future.successful(BadRequest)
    } recover {
      case ex =>
        ServiceUnavailable
    }
  }

  def list = Action.async {
    productRepo.list("ACTIVE") map { dbResult =>
      val listOfProducts = dbResult map {
        case product =>
          ListProductsResponse(id = UUID.randomUUID().toString, title = product.title,
            `type` = product.`type`, minAmount = product.minAmount, description = product.description,
            startTime = convertBsonDateTimeToZonedDateTime(product.startTime),
            endTime = convertBsonDateTimeToZonedDateTime(product.endTime),
            createdTime = convertBsonDateTimeToZonedDateTime(product.createdTime),
            finishedTime = None)
      }
      Ok(Json.toJson(listOfProducts))
    } recover {
      case ex =>
        ServiceUnavailable
    }
  }

  def get(id: String) = Action.async {
    productRepo.get(id) map {
      case Some(dbResult) =>
        Ok(Json.toJson(dbResult))
      case None =>
        NotFound
    } recover {
      case ex =>
        ServiceUnavailable
    }
  }

  private  def convertBsonDateTimeToZonedDateTime(bSONDateTime: BSONDateTime): ZonedDateTime = {
    val i = Instant.ofEpochSecond(bSONDateTime.value)
    ZonedDateTime.ofInstant(i, ZoneId.of("UTC"))
  }

  private def buildProduct(createProductRequest: CreateProductRequest): Product = {
    BSONDateTime(ZonedDateTime.now().toEpochSecond)
    Product(id = UUID.randomUUID().toString, title = createProductRequest.title,
      maxAmount = createProductRequest.maxAmount,
      `type` = createProductRequest.`type`, minAmount = createProductRequest.minAmount,
      description = createProductRequest.description,
      startTime = BSONDateTime(createProductRequest.startTime.toEpochSecond),
      endTime = BSONDateTime(createProductRequest.endTime.toEpochSecond),
      createdTime = BSONDateTime(ZonedDateTime.now.toEpochSecond), isOpen = true, chargeFee = None)
    }
}
