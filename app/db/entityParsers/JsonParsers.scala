package db.entityParsers

import play.api.libs.json.{JsObject, JsValue, Reads}
import play.api.mvc.{AnyContent, Request}
import playUtils.{JsonBodyParseError, PlayError}

trait JsonParsers[E] {

  import Reads._

  def fromRequest(implicit req: Request[AnyContent]): Either[PlayError, E] = {
    req.body.asJson match {
      case Some(json) => parseEntity(json)
      case None => Left(JsonBodyParseError("Json payload not found"))
    }
  }

  protected def parseEntity(implicit json: JsValue): Either[PlayError, E]

  protected def parse[T](key: String)(implicit json: JsValue, reads: Reads[T]): Either[PlayError, T] = {
    json match {
      case JsObject(fields) =>
        fields.collectFirst {
          case (k, value) if k == key => value.as[T]
        } match {
          case Some(result) => Right(result)
          case None => Left(JsonBodyParseError(s"'$key' could not be parsed in json body"))
        }
      case _ => Left(JsonBodyParseError("Json payload is not JsObject"))
    }
  }

  protected def parseOptional[T](key: String)(implicit json: JsValue, reads: Reads[T]): Either[PlayError, Option[T]] = {
    parse[T](key) match {
      case Right(value) => Right(Some(value))
      case Left(error) => Right(None)
    }
  }
}