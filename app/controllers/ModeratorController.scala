package controllers

import javax.inject._

import db.Tables
import db.Tables.UserRow
import db.dao.{MessageDao, ModeratorDao}
import play.api.libs.json._
import play.api.mvc._
import playUtils.RichErrorEither._
import playUtils.{ControllerWithAuthentication, LoggedInRequest}
import services.{UserInfo, UserService}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ModeratorController @Inject()(cc: ControllerComponents, userService: UserService, moderatorDao: ModeratorDao, messageDao: MessageDao)(implicit assetsFinder: AssetsFinder)
  extends ControllerWithAuthentication(cc, userService) {

  val userWrites = Tables.userRowWrites
  val userInfoWrites = Json.writes[UserInfo]



  def unresolvedComplaints() = loggedInAction { case LoggedInRequest(user, req) =>
    if(!user.ismoderator) {
      Forbidden("Not moderator")
    } else {
      val res = moderatorDao.unresolvedComplaints().map {
        case (user, reasons, nComplaints) =>
          val messages = messageDao.getByUser(user.id).map(_.messagetext)
          JsObject(Map(
            "user" -> userWrites.writes(user),
            "reasons" -> JsString(reasons),
            "count" -> JsNumber(nComplaints),
            "messages" -> JsArray(messages.map(JsString))
          ))
      }

      val resJson = JsArray(res)

      Ok(resJson)
    }
  }

  def banUser(id: Long) = loggedInAction { case LoggedInRequest(user, req) =>
    if(!user.ismoderator) {
      Forbidden("Not moderator")
    } else {
      moderatorDao.banUser(id, moderator = user)
      NoContent
    }
  }

  def forgiveUser(id: Long) = loggedInAction { case LoggedInRequest(user, req) =>
    if(!user.ismoderator) {
      Forbidden("Not moderator")
    } else {
      moderatorDao.forgiveUser(id, moderator = user)
      NoContent
    }
  }

  def report() = Action { req =>
    val stats1 = moderatorDao.countOfModeratorsWithAllResolvedComplaints()
    val stats2 = moderatorDao.countOfUsersWithAtLeastNFriends(2)
    val stats3 = moderatorDao.countOfUsersWithAtLeastNMessages(3)
    val stats4 = moderatorDao.countNumberOfDialogsWhereEveryoneHasWrittenAtLeastNTimes(3)

    val res =
      s"""
        | Count of moderators with all resolved complaints: $stats1
        | Count of users with at least 2 friends: $stats2
        | Count of users with at least 3 messages: $stats3
        | Count of dialogs where ALL members have written at least 3 messages each: $stats4
      """.stripMargin

    Ok(res)
  }

}
