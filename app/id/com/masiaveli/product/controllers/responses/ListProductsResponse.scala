package id.com.masiaveli.product.controllers.responses

import java.time.ZonedDateTime

import id.com.masiaveli.product.model.MultiCurrencyMoney
import reactivemongo.bson.BSONDateTime

case class ListProductsResponse
(
  id: String,
  title: String,
  `type`: String,
  minAmount: MultiCurrencyMoney,
  description: Option[String],
  startTime: ZonedDateTime,
  endTime: ZonedDateTime,
  createdTime: ZonedDateTime,
  finishedTime: Option[ZonedDateTime]
)

