package db

import play.api.{Configuration, Play}
import playUtils.Config
import slick.dbio.{DBIOAction, NoStream}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object Db {

  private val db = slick.jdbc.JdbcBackend.Database.forURL(
    url=Config.getStr("db.url"),
    driver="com.mysql.jdbc.Driver",
    user = Config.getStr("db.username"),
    password = Config.getStr("db.password")
  )

  implicit class ActionToResult[R](action: DBIOAction[R, NoStream, Nothing]) {
    def get(): R = Await.result(db.run(action), 60 seconds)
    def exec() : Unit = get()
  }


}
