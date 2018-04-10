package services

import javax.inject.{Inject, Singleton}

import _root_.db.dao.UserDao
import _root_.db.entityParsers.UserAvatar
import db.Tables.UserRow
import playUtils._


@Singleton
class UserService @Inject()(userDao : UserDao, attachmentService: AttachmentService) {

  def getUserById(id: Long): Either[PlayError, UserRow] = {
    userDao.findById(id) match {
      case Some(user) => Right(user)
      case None => Left(UserNotFound)
    }
  }

  def getAllByQuery(query: String): Seq[UserRow] = {
    userDao.findAllByQuery(query)
  }

  def findByAuthToken(token: String): Option[UserRow] = {
    userDao.findByAuthToken(token)
  }

  def updateUserProfile(user: UserRow, avatarUrl: String): UserRow = {

    val userWithAttachment =
      if(avatarUrl.trim.nonEmpty) {
        val attachment = attachmentService.add(avatarUrl)
        user.copy(avatarid = Some(attachment.id.toInt))
      } else {
        user
      }

    userDao.update(userWithAttachment)
  }

}
