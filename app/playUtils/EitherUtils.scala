package playUtils



object EitherUtils {
  // Defines right projection as an implicit projection for Either
  // which makes it possible to do Either-based for-comprehensions
  implicit class RightBiasedEither[A, B](val e: Either[A, B]) extends AnyVal {
    def foreach[U](f: B => U): Unit = e.right.foreach(f)
    def map[C](f: B => C): Either[A, C] = e.right.map(f)
    def flatMap[C](f: B => Either[A, C]): Either[A, C] = e.right.flatMap(f)
    def withFilter(p: B => Boolean): Either[A, B] = e.withFilter(p)
  }
}