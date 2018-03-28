package slickGenerator

import db.Tables
import Tables.profile.api._


import scala.concurrent.Await

import db.Db._

object DbTest extends App {


  val q = Tables.Message.result.get()

  println(q)


}
