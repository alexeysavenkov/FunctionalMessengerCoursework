package slickGenerator

import slick.driver.MySQLDriver

object Generator extends App {
  // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
//  val url = "jdbc:h2:mem:test;INIT=runscript from 'app/slickGenerator/sql/create.sql'"
  //val db = Database.forURL(url, driver = "org.h2.Driver")
  val profile = "slick.jdbc.MySQLProfile"
  val url = "jdbc:mysql://mmop-db.cs9tngvh7qnb.us-east-2.rds.amazonaws.com/ais?nullNamePatternMatchesAll=true"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val user = "admin"
  val password = "zzzz1234"
  val pkg = "com.example.models"
  val outputFolder = "/Users/alosha/IdeaProjects/FunctionalCoursework/slick"

  slick.codegen.SourceCodeGenerator.main(
    Array(profile, jdbcDriver, url, outputFolder, pkg, user, password)
  )

  // Using generated code. Our Build.sbt makes sure they are generated before compilation.
//  val q = Companies.join(Computers).on(_.id === _.manufacturerId)
//    .map { case (co, cp) => (co.name, cp.name) }

//  Await.result(db.run(q.result).map { result =>
//    println(result.groupBy { case (co, cp) => co }
//      .mapValues(_.map { case (co, cp) => cp })
//      .mkString("\n")
//    )
//  }, 60 seconds)
}