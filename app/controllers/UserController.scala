package controllers

import javax.inject._

import db.Tables
import db.Tables.UserRow
import db.entityParsers.User
import play.api.libs.json._
import play.api.mvc._
import playUtils.{ControllerWithAuthentication, LoggedInRequest, PlayError}
import playUtils.RichErrorEither._
import services.{AuthService, UserInfo, UserService}

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService)(implicit assetsFinder: AssetsFinder)
  extends ControllerWithAuthentication(cc, userService) {

  def friends() = loggedInAction { case LoggedInRequest(user, req) =>

    val friends =
      userService.getFriends(user)
        .map(u => (u, userService.getUserInfoById(user, u.id)))
        .map(userWithInfoWrites.writes)

    Ok(JsArray(friends))
  }

  import playUtils.EitherUtils.RightBiasedEither

  val userWrites = Tables.userRowWrites
  val userInfoWrites = Json.writes[UserInfo]

  val userWithInfoWrites = new OWrites[(UserRow, UserInfo)] {
    override def writes(o: (_root_.db.Tables.UserRow, UserInfo)): JsObject =
      JsObject(Map(
        "user" -> userWrites.writes(o._1),
        "userInfo" -> userInfoWrites.writes(o._2)
      ))
  }

  def updateProfile() = loggedInAction { case LoggedInRequest(user, req) =>
    req.body.asJson match {
      case Some(json) =>
        val name = (json \ "name").as[String]
        val description = (json \ "description").as[String]
        val avatarUrl = (json \ "avatarUrl").as[String]

        val updatedUser = userService.updateUserProfile(user.copy(name = Some(name), description = description), avatarUrl)

        Ok(userWrites.writes(updatedUser))

      case None => NoContent
    }
  }

  def getUserById(id: Long) = loggedInAction { case LoggedInRequest(user, req) =>
    userService.getUserById(id).toResult(userWrites)
  }

  def getUserInfoById(id: Long) = loggedInAction { case LoggedInRequest(user, req) =>
    Ok(Json.writes[UserInfo].writes(userService.getUserInfoById(user, id)))
  }

//  def getUserPublicPostsById(id: Int) = Action {
//
//  }
//
  def searchUsersByQuery(query: String) = loggedInAction { req =>
    val users = userService.getAllByQuery(query)

    Ok(JsArray(users.map(userWrites.writes).toList))
  }


  def friendRequest(id: Long, cancel: Int) = loggedInAction { case LoggedInRequest(user, req) =>

    if(cancel > 0) {
      userService.cancelFriendRequest(from = user.id, to = id)
      NoContent
    } else {
      userService.createFriendRequest(from = user.id, to = id)
      NoContent
    }

  }

  def blacklist(id: Long, cancel: Int) = loggedInAction { case LoggedInRequest(user, req) =>

    if(cancel > 0) {
      userService.cancelBlacklist(blacklist = id, by = user.id)
      NoContent
    } else {
      userService.createBlacklist(blacklist = id, by = user.id)
      NoContent
    }

  }

  def report(id: Long, reason: String) = loggedInAction { case LoggedInRequest(user, req) =>

    userService.report(currentUser = user, userId = id, reason = reason)
    NoContent

  }



  def friendRequests() = loggedInAction { case LoggedInRequest(user, req) =>
    val friends = userService.getFriendRequests(user).map(u => (u, userService.getUserInfoById(user, u.id))).map(userWithInfoWrites.writes)
    Ok(JsArray(friends))
  }

//
//  def sendFriendRequest()

}
