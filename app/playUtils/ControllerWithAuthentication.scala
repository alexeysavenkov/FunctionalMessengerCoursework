package playUtils

import javax.inject.Inject

import db.Tables.UserRow
import db.dao.UserDao
import play.api.mvc._
import services.UserService

import scala.concurrent.{ExecutionContext, Future}


case class LoggedInRequest[A](user: UserRow, request: Request[A]) extends WrappedRequest[A](request)

class ControllerWithAuthentication(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  object LoggedInAction extends ActionBuilder[LoggedInRequest, AnyContent] {
    override def invokeBlock[A](request: Request[A], block: LoggedInRequest[A] => Future[Result]): Future[Result] = {

      val userOpt =
        if(Config.isDebug()) {
          userService.getUserById(1).toOption
        } else {
          for {
            token <- request.headers.get("Authorization")
            user <- userService.findByAuthToken(token)
          } yield user
        }

      userOpt match {
        case Some(user) => block(LoggedInRequest(user, request))
        case None => Future.successful(Results.Unauthorized("Authorization token incorrect or missing"))
      }

    }

    override def parser: BodyParser[AnyContent] = Action.parser

    override def executionContext: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  }

  def loggedInAction(block: LoggedInRequest[AnyContent] => Result): Action[AnyContent] = {
    LoggedInAction(block)
  }
}

