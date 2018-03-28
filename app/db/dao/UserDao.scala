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

  protected def findSingle(predicate: User => Rep[Boolean]): Option[UserRow] = {
    User.filter(predicate).result.headOption.get()
  }

  def insert(userRow: UserRow) = (User += userRow).exec()
}
