package services

import javax.inject.{Inject, Singleton}

import db.Tables.{User, UserRow}
import db.Db._
import _root_.db.Tables.profile.api._
import _root_.db.dao.UserDao
import _root_.db.entityParsers.UserAvatar
import playUtils.Errors._
import playUtils.{DuplicateUserException, IncorrectCredentials, PlayError, SystemError}


@Singleton
class AuthService @Inject()(userDao : UserDao) {

  def register(user: UserRow, avatarOpt: Option[UserAvatar]): Either[PlayError, UserRow] = {

    userDao.findByPhone(user.phone) match {
      case Some(duplicateUser) => Left(DuplicateUserException)
      case None =>
        val authToken = new String(scala.util.Random.alphanumeric.take(32).toArray)
        val userWithToken = user.copy(authToken = authToken)
        userDao.insert(userWithToken)
        userDao.findByPhone(user.phone) match {
          case Some(newUser) => Right(newUser)
          case None => Left(SystemError("New user could not be created"))
        }
    }

  }

  def login(user: UserRow): Either[PlayError, UserRow] = {
    userDao.findByPhone(user.phone) match {
      case Some(userToLogin) if userToLogin.password == user.password => Right(userToLogin)
      case _ => Left(IncorrectCredentials)
    }
  }
}
