package id.com.masiaveli.product.repositories

import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.commands.WriteResult

import scala.concurrent.{ExecutionContext, Future}
import play.modules.reactivemongo.json._
import reactivemongo.api.ReadPreference
import reactivemongo.bson.{BSONDateTime, BSONDocument}
import id.com.masiaveli.product.model.Product
import id.com.masiaveli.product.model.ModelsFormatter._

class ProductRepoImpl @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext) extends ProductRepo {

  protected def collection =
    reactiveMongoApi.db.collection[JSONCollection]("products")
  override def create(document: Product): Future[WriteResult] = {
    collection.insert(document)
  }
  override def list(status: String): Future[List[Product]] = {
    val query = status match {
      case "ACTIVE" =>
        Json.obj("isOpen"->true, "startTime.value" -> Json.obj("$lte" -> (ZonedDateTime.now().toEpochSecond)))
      case "FINISHED" =>
        Json.obj("isFinished"->true)
      case "CLOSED" =>
        Json.obj("isClosed"->true)
      case "CANCELED" =>
        Json.obj("isCanceled"->true)
    }

    collection.find(query).sort(Json.obj("createdTime"-> -1)).
      cursor[Product](ReadPreference.Primary).collect[List]()
  }
  override def get(id: String): Future[Option[Product]] = {
    val query = BSONDocument("id" -> id)
    collection.find(query).one[Product]
  }
}
