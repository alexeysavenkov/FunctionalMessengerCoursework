package services

import javax.inject.{Inject, Singleton}

import db.Tables.User
import db.Db._
import _root_.db.Tables.profile.api._
import _root_.db.dao.UserDao
import _root_.db.entityParsers.UserAvatar
import playUtils.Errors._
import playUtils.{DuplicateUserException, PlayError, SystemError}


@Singleton
class AuthService @Inject()(userDao : UserDao) {

  def register(user: User#TableElementType, avatarOpt: Option[UserAvatar]): Either[PlayError, User#TableElementType] = {



    userDao.findByPhone(user.phone) match {
      case Some(duplicateUser) => Left(DuplicateUserException)
      case None =>
        val authToken = new String(scala.util.Random.alphanumeric.take(32).toArray)
        val userWithToken = user.copy(authToken = authToken)
        User.insertOrUpdate(userWithToken).exec()
        userDao.findByPhone(user.phone) match {
          case Some(newUser) => Right(newUser)
          case None => Left(SystemError("New user could not be created"))
        }
    }

  }
}
