package playUtils

import play.api.libs.json.{Json, OWrites, Writes}
import play.api.libs.json._

class OWritesOps[A](writes: OWrites[A]) {
//  def addField[T: Writes](fieldName: String, field: A => T): OWrites[A] =
//    (writes ~ (__ \ fieldName).write[T])((a: A) => (a, field(a)))


  def removeField(fieldName: String): OWrites[A] = OWrites { a: A =>
    val transformer = (__ \ fieldName).json.prune
    Json.toJson(a)(writes).validate(transformer).get
  }
}

object OWritesOps {
  implicit def from[A](writes: OWrites[A]): OWritesOps[A] = new OWritesOps(writes)
}