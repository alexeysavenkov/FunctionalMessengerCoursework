package controllers

import javax.inject._

import db.Tables
import db.Tables.DialogRow
import play.api.libs.json._
import play.api.mvc._
import playUtils.RichErrorEither._
import playUtils.{ControllerWithAuthentication, LoggedInRequest}
import services.{DialogService, MessageService, UserInfo, UserService}

import scala.concurrent.Await

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class DialogController @Inject()(cc: ControllerComponents, userService: UserService, dialogService: DialogService, messageService: MessageService)(implicit assetsFinder: AssetsFinder)
  extends ControllerWithAuthentication(cc, userService) {

  val userWrites = Tables.userRowWrites

  def getAllDialogs() = loggedInAction { case LoggedInRequest(user, req) =>
    val dialogs = dialogService.getAllDialogsByUserMember(user)
    Ok(JsArray(dialogs.map(d => JsObject(Map("id" -> JsNumber(d.id), "name" -> JsString(d.name))))))
  }

  def create(name: String) = loggedInAction { case LoggedInRequest(user, req) =>
    val members = req.body.asJson.get.asInstanceOf[JsArray].value.map(_.as[Long])
    dialogService.create(name, admin = user, members)
    NoContent
  }

  def getAllMessages(dialogId: Long) = loggedInAction { case LoggedInRequest(user, req) =>
     val messages: Seq[(_root_.db.Tables.UserRow, String)] = dialogService.getAllMessages(dialogId)

     val res = messages.map({
       case (user, message) => JsObject(Map("user" -> userWrites.writes(user), "message" -> JsString(message)))
     })

     Ok(JsArray(res))
  }

  def sendMessage(dialogId: Long, message: String) = loggedInAction { case LoggedInRequest(user, req) =>
    val attachments = req.body.asJson.getOrElse(JsArray()).asInstanceOf[JsArray].value.map(_.as[String])
    messageService.createMessage(user.id, dialogId, message, attachments)

    val f = getAllMessages(dialogId).apply(req)
    import scala.concurrent.duration._
    val res = Await.result(f, 10 seconds)
    res
  }

}
