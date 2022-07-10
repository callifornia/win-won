trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def lift[A, B](f: A => B): F[A] => F[B] =
    fa => map(fa)(f)
}

val asd = new Functor[Option] {
  def map[A, B](fa: Option[A])(f: A => B) = fa.map(f)
}

val ss: Option[String] => Option[Int] =
  asd.lift((s: String) => s.toInt)


ss(Some("123"))
