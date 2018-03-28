package playUtils

import akka.util.ByteString
import play.api.PlayException
import play.api.http.Status._
import play.api.http.Writeable
import play.api.libs.json.Writes
import play.api.mvc.Result
import play.api.mvc.Results._

object Errors {

}

object RichErrorEither {
  implicit class errorEitherToStatus[T](either: Either[PlayError, T]) {
    def toResult(implicit writeable: Writeable[T]): Result =
      either match {
        case Left(error) => new Status(error.status)(error.message)
        case Right(content) => Ok(content)
      }

    def toResult(writes: Writes[T]): Result = {
      val writeable = new Writeable[T](
        transform = x => ByteString(writes.writes(x).toString),
        contentType = Some("application/json")
      )
      toResult(writeable)
    }
  }
}

class PlayError(val status: Int, val message: String)

case class SystemError(msg: String) extends PlayError(INTERNAL_SERVER_ERROR, msg)

object DuplicateUserException extends PlayError(CONFLICT, "You are already registered")

object IncorrectCredentials extends PlayError(UNAUTHORIZED, "Incorrect phone or password")

case class JsonBodyParseError(msg: String) extends PlayError(BAD_REQUEST, msg)

object UserNotFound extends PlayError(NOT_FOUND, "User not found")
