package id.com.masiaveli.product.model

import play.api.libs.json.Json

object ModelsFormatter extends CommonFormatter{
  implicit val productModelReader = Json.reads[Product]
  implicit val productModelWriter = Json.writes[Product]
}
