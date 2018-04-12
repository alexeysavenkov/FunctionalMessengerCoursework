package db.dao

import javax.inject.Singleton

import db.Tables._
import db.Tables.profile.api._
import db.Db._
import slick.lifted.CanBeQueryCondition

@Singleton
class UserDao {
  def findById(id: Long): Option[UserRow] = {
    findSingle(_.id === id)
  }

  def findByPhone(phone: String): Option[UserRow] = {
    findSingle(_.phone === phone)
  }

  def findByAuthToken(token: String): Option[UserRow] = {
    findSingle(_.authtoken === token)
  }

  def findAllByQuery(query: String): Seq[UserRow] = {
    User.filter(user => user.name.like(s"%$query%") || user.description.like(s"%$query%")).result.get()
  }

  def update(user: UserRow): UserRow = {
    User.insertOrUpdate(user).exec()
    findById(user.id).get
  }

  protected def findSingle(predicate: User => Rep[Boolean]): Option[UserRow] = {
    User.filter(predicate).result.headOption.get()
  }

  def insert(userRow: UserRow) : Unit = (User += userRow).exec()

  def areFriends(user1: Long, user2: Long) : Boolean = {
     Friendrequest
       .filter(x => ((x.friendrequestby === user1 && x.friendrequestto === user2) || (x.friendrequestby === user2 && x.friendrequestto === user1)) && x.isconfirmed)
       .result.headOption.get().isDefined
  }

  def isFriendRequestSent(from: Long, to: Long) : Boolean = {
    Friendrequest
      .filter(x => x.friendrequestby === from && x.friendrequestto === to && !x.isconfirmed)
      .result.headOption.get().isDefined
  }

  def isBlacklisted(who: Long, by: Long) : Boolean = {
    Blacklist
      .filter(x => x.blacklistedby === by && x.blacklisteduser === who)
      .result.headOption.get().isDefined
  }

  def createFriendRequest(from: Long, to: Long): Unit = {
    val q = Friendrequest.filter(x => x.friendrequestto.inSet(Set(to, from)) && x.friendrequestby.inSet(Seq(to, from)))
    q.result.headOption.get() match {
      case None =>
        (Friendrequest += FriendrequestRow(friendrequestto = to, friendrequestby = from)).exec()
      case Some(friendRequest) =>
        q.delete.exec()
        (Friendrequest += friendRequest.copy(isconfirmed = true)).exec()
    }
  }


  def cancelFriendRequest(from: Long, to: Long): Unit = {
    Friendrequest.filter(x => x.friendrequestto.inSet(Set(to, from)) && x.friendrequestby.inSet(Seq(to, from))).delete.exec()
  }

  def createBlacklist(blacklist: Long, by: Long): Unit = {
    (Blacklist += BlacklistRow(blacklisteduser = blacklist, blacklistedby = by)).exec()
  }

  def cancelBlacklist(blacklist: Long, by: Long): Unit = {
    Blacklist.filter(x => x.blacklisteduser === blacklist && x.blacklistedby === by).delete.exec()
  }

  def report(currentUser: UserRow, userId: Long, reasonWhy: String): Unit = {
    (Usercomplaint += UsercomplaintRow(
        id = -1,
        userwhocomplained =
        currentUser.id,
        userwhowasaccused = userId,
        reason = reasonWhy,
        assignedtomoderator = None,
        isresolved = false)
      ).exec()
  }

  def getFriends(user: UserRow): Seq[UserRow] = {
    val friendIds = Friendrequest.filter(x => (x.friendrequestto === user.id || x.friendrequestby === user.id) && x.isconfirmed)
      .result.get().flatMap(x => Seq(x.friendrequestby, x.friendrequestto).filterNot(_ == user.id)).toSet

    User.filter(x => x.id.inSet(friendIds)).result.get()
  }

  def getFriendRequests(user: UserRow): Seq[UserRow] = {
    val friendIds = Friendrequest.filter(x => x.friendrequestto === user.id && !x.isconfirmed)
      .result.get().flatMap(x => Seq(x.friendrequestby, x.friendrequestto).filterNot(_ == user.id)).toSet

    User.filter(x => x.id.inSet(friendIds)).result.get()
  }

}
