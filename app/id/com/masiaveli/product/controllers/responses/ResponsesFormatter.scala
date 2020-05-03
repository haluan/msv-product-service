package id.com.masiaveli.product.controllers.responses

import id.com.masiaveli.product.model.CommonFormatter
import play.api.libs.json._

object ResponsesFormatter extends CommonFormatter {
  implicit val createProductResponseWriter = Json.writes[CreateProductResponse]
  implicit val listProductsResponseWriter = Json.writes[ListProductsResponse]
}
