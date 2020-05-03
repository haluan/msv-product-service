package id.com.masiaveli.product.model

import java.time.ZonedDateTime

import reactivemongo.bson.BSONDateTime

case class Product
(
  id: String,
  title: String,
  `type`: String,
  maxAmount: Option[MultiCurrencyMoney],
  minAmount: MultiCurrencyMoney,
  description: Option[String],
  startTime: BSONDateTime,
  endTime: BSONDateTime,
  createdTime: BSONDateTime,
  isOpen: Boolean,
  chargeFee: Option[MultiCurrencyMoney]
)
