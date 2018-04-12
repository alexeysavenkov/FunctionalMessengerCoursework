package services

import javax.inject.{Inject, Singleton}

import _root_.db.dao.UserDao
import _root_.db.entityParsers.UserAvatar
import db.Tables.UserRow
import playUtils._


case class UserInfo(userId: Long, isFriend: Boolean, friendRequestSent: Boolean, friendRequestReceived: Boolean, isBlacklisted: Boolean, youAreBlacklisted: Boolean)
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

  def getUserInfoById(currentUser: UserRow, id: Long): UserInfo = {
    UserInfo(
      userId = id,
      isFriend = userDao.areFriends(currentUser.id, id),
      friendRequestSent = userDao.isFriendRequestSent(from = currentUser.id, to = id),
      friendRequestReceived = userDao.isFriendRequestSent(from = id, to = currentUser.id),
      isBlacklisted = userDao.isBlacklisted(who = id, by = currentUser.id),
      youAreBlacklisted = userDao.isBlacklisted(who = currentUser.id, by = id)
    )
  }

  def createFriendRequest(from: Long, to: Long): Unit = {
    userDao.createFriendRequest(from, to)
  }

  def cancelFriendRequest(from: Long, to: Long): Unit = {
    userDao.cancelFriendRequest(from, to)
  }

  def createBlacklist(blacklist: Long, by: Long): Unit = {
    userDao.createBlacklist(blacklist, by)
  }

  def cancelBlacklist(blacklist: Long, by: Long): Unit = {
    userDao.cancelBlacklist(blacklist, by)
  }

  def report(currentUser: UserRow, userId: Long, reason: String): Unit = {
    userDao.report(currentUser, userId, reason)
  }

  def getFriends(user: UserRow): Seq[UserRow] = {
    userDao.getFriends(user)
  }

  def getFriendRequests(user: UserRow): Seq[UserRow] = {
    userDao.getFriendRequests(user)
  }
}
