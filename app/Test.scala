object Test extends App {


  val fibs: Stream[BigInt] = BigInt(0) #::
    BigInt(1) #::
    fibs.zip(fibs.tail).map { n => n._1 + n._2 }

  println(fibs.take(10).toList) // List(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)

}
