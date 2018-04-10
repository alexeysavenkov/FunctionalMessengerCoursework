package controllers

import javax.inject._

import db.Tables
import db.Tables.UserRow
import db.entityParsers.User
import play.api.libs.json.JsArray
import play.api.mvc._
import playUtils.{ControllerWithAuthentication, LoggedInRequest, PlayError}
import playUtils.RichErrorEither._
import services.{AuthService, UserService}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService)(implicit assetsFinder: AssetsFinder)
  extends ControllerWithAuthentication(cc, userService) {

  import playUtils.EitherUtils.RightBiasedEither

  val userWrites = Tables.userRowWrites

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

//  def getUserPublicPostsById(id: Int) = Action {
//
//  }
//
  def searchUsersByQuery(query: String) = loggedInAction { req =>
    val users = userService.getAllByQuery(query)

    Ok(JsArray(users.map(userWrites.writes).toList))
  }
//
//  def sendFriendRequest()

}
