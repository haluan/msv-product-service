package id.com.masiaveli.product.controllers.requests

import java.time.ZonedDateTime

import id.com.masiaveli.product.model.{CommonFormatter, MultiCurrencyMoney}
import play.api.libs.json._

import scala.util.Try

object RequestsFormatter extends CommonFormatter {
  implicit val createProductRequestReader = Json.reads[CreateProductRequest]
}
