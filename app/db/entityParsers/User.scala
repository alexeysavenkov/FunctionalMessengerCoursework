package db.entityParsers

import db.Tables.{User, UserRow}
import play.api.libs.json.{JsObject, JsValue, Json, Reads}
import play.api.mvc.{AnyContent, Request}
import playUtils.{JsonBodyParseError, PlayError}
import playUtils.Errors._
import playUtils.EitherUtils._


case class UserAvatar(attachmentId : Int) extends AnyVal

object User extends JsonParsers[(UserRow, Option[UserAvatar])] {

  override protected def parseEntity(implicit json: JsValue): Either[PlayError, (UserRow, Option[UserAvatar])] = {
    for {
      phone <- parse[String]("phone")
      password <- parse[String]("password")
      name <- parse[String]("name")
      avatar <- parseOptional[Int]("avatar")
      description <- parseOptional[String]("description")
    } yield {
      val user = new UserRow(
        id = -1,
        phone = phone,
        password = password,
        name = Some(name),
        description = description.getOrElse("")
      )
      val avatarOpt = avatar.map(UserAvatar)
      (user, avatarOpt)
    }
  }
}


