package services

import javax.inject.{Inject, Singleton}

import _root_.db.dao.UserDao
import _root_.db.entityParsers.UserAvatar
import db.Tables.UserRow
import playUtils._


@Singleton
class UserService @Inject()(userDao : UserDao) {

  def getUserById(id: Long): Either[PlayError, UserRow] = {
    userDao.findById(id) match {
      case Some(user) => Right(user)
      case None => Left(UserNotFound)
    }
  }

  def findByAuthToken(token: String): Option[UserRow] = {
    userDao.findByAuthToken(token)
  }

}
