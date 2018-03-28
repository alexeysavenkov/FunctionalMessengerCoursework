package db.dao

import javax.inject.Singleton

import db.Tables._
import db.Tables.profile.api._
import db.Db._

@Singleton
class UserDao {
  def findByPhone(phone: String): Option[User#TableElementType] = {
    User.filter(_.phone === phone).result.headOption.get()
  }
}
