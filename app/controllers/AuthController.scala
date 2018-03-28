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

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  import playUtils.EitherUtils.RightBiasedEither


  def register = Action { req =>

    val result: Either[PlayError, UserRow] =
      for {
        (user, avatar) <- User.fromRequest(req)
        result <- authService.register(user, avatar)
      } yield result

    result.toResult(Tables.userRowWrites)
  }

}
