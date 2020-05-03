package id.com.masiaveli.product.model

import java.time.{ZoneId, ZonedDateTime}
import java.time.format.DateTimeFormatter

import play.api.libs.json._
import reactivemongo.bson.BSONDateTime

import scala.util.Try

abstract class CommonFormatter {

  implicit val bsonDateTimeResponseWriter = Json.writes[BSONDateTime]
  implicit val bsonDateTimeResponsereader = Json.reads[BSONDateTime]

  implicit val multiCurrencyModelMoneyReader = Json.reads[MultiCurrencyMoney]
  implicit val multiCurrencyModelMoneyWriter = Json.writes[MultiCurrencyMoney]

  implicit val zonedDateTimeWriter = new Writes[ZonedDateTime] {
    override def writes(d: ZonedDateTime): JsValue = JsString(toZonedDateTimeToUTCISOFormat(d))
  }

  implicit val zonedDatetimeReader = new Reads[ZonedDateTime] {
    override def reads(json: JsValue) = json.validate[String] flatMap { value =>
      Try(ZonedDateTime.parse(value)).map(result => JsSuccess(result)).getOrElse(
        JsError(s"$value is not in correct datetime format"))
    }
  }

  private val ISO_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
  private def toZonedDateTimeToUTCISOFormat(zonedDateTime: ZonedDateTime): String = {
    val zoneID = ZoneId.of("UTC")
    val timeToUTC = zonedDateTime.withZoneSameInstant(zoneID)
    val dateTimeFormatter = DateTimeFormatter.ofPattern(ISO_DATE_TIME_PATTERN)
    timeToUTC.format(dateTimeFormatter)
  }
}
