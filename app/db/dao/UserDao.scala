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
    findSingle(_.authtoken == token)
  }

  def findAllByQuery(query: String): Seq[UserRow] = {
    User.filter(user => user.name.like(s"%$query%") || user.description.like(s"%$query%")).result.get()
  }

  def update(user: UserRow): UserRow = {
    User.insertOrUpdate(user)
    findById(user.id).get
  }

  protected def findSingle(predicate: User => Rep[Boolean]): Option[UserRow] = {
    User.filter(predicate).result.headOption.get()
  }

  def insert(userRow: UserRow) : Unit = (User += userRow).exec()
}
