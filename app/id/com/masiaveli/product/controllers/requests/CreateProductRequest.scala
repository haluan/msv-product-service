package id.com.masiaveli.product.controllers.requests

import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

import id.com.masiaveli.product.model.MultiCurrencyMoney

case class CreateProductRequest
(
  title: String,
  `type`: String,
  maxAmount: Option[MultiCurrencyMoney],
  minAmount: MultiCurrencyMoney,
  description: Option[String],
  startTime: ZonedDateTime,
  endTime: ZonedDateTime
){
  def validateTimeLength: Boolean = {
    val timeGap = ChronoUnit.MILLIS.between(startTime, endTime)
    (timeGap >= 0) match {
      case false =>
        false
      case _ =>
        true
    }
  }
}
