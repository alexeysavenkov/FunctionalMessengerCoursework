package controllers

import javax.inject._

import db.Tables
import db.Tables.UserRow
import db.entityParsers.User
import play.api.libs.json.{Json, OWrites, Writes}
import play.api.mvc._
import playUtils.PlayError
import playUtils.RichErrorEither._
import services.AuthService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AuthController @Inject()(cc: ControllerComponents, authService: AuthService)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  import playUtils.EitherUtils.RightBiasedEither

  val userWrites = Tables.userRowWrites

  def register() = Action { req =>

    val result: Either[PlayError, UserRow] =
      for {
        (user, avatar) <- User.fromRequest(req)
        result <- authService.register(user, avatar)
      } yield result

    result.toResult(userWrites)
  }

  def login() = Action { req =>

    val result: Either[PlayError, UserRow] =
      for {
        (user, _) <- User.fromRequest(req)
        result <- authService.login(user)
      } yield result

     result.toResult(userWrites)
  }



}
