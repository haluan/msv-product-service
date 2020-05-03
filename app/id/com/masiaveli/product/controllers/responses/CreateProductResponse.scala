package id.com.masiaveli.product.controllers.responses

import java.time.ZonedDateTime

import reactivemongo.bson.BSONDateTime

case class CreateProductResponse
(
  id: String,
  createdTime: BSONDateTime
)
