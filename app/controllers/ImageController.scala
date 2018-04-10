package controllers

import java.nio.file.Paths
import javax.inject.Inject

import org.apache.commons.codec.binary.Base64
import play.api.mvc.ControllerComponents
import playUtils.{ControllerWithAuthentication, LoggedInRequest, PlayError}
import services.{AttachmentService, UserService}
import playUtils.RichErrorEither._


class ImageController @Inject()(cc: ControllerComponents, attachmentService: AttachmentService, userService: UserService)(implicit assetsFinder: AssetsFinder)
  extends ControllerWithAuthentication(cc, userService){

  def uploadImage = Action(parse.multipartFormData) { request =>
    request.body.file("image").map { picture =>
      attachmentService.upload(picture)
      Ok("File uploaded")
    }.getOrElse {
      BadRequest("Image not found")
    }
  }


  def getImage(attachmentId: Int) = loggedInAction { case LoggedInRequest(user, request) =>
    attachmentService.getById(attachmentId, user).map { attachment =>
      val url = attachment.imageurl
      val tokens = url.split("\\;base64\\,")
      val (contentType, base64) =
        if(tokens.length > 1) {
          (tokens(0).replace("data:", ""), tokens(1))
        } else {
          ("image/jpeg", tokens(0))
        }
      val imageBytes = Base64.decodeBase64(base64)
      Ok(imageBytes).as(contentType)
    } match {
      case Right(res) => res
      case Left(err) => Left[PlayError, String](err).toResult
    }
  }
}
