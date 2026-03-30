
/*
    Law:
      left identity:    Monad(x).flatMap(x => Monad(x)) === Monad(x)
      right identity:   Monad(x).flatMap(f) === f(x)
      composition:      Monad(x).flatMap(f).flatMap(g) == Monad(x).flatMap(x => f(x).flatMap(g))
* */








// Semigroup
trait Semigroup[T] {
  def combine(a: T, b: T): T
}


trait Monoid[T] extends Semigroup[T] {
  def empty: T
}


implicit val intSemi = new Semigroup[Int] {
  def combine(a: Int, b: Int): Int = (a + 2) + (b + 2)
}


implicit val strSemi = new Semigroup[String] {
  override def combine(a: String, b: String): String = (a + "_append(a)_") + (b + "_append(b)_")
}


implicit class SemigroupOps[T](value: T) {
  def combineI(other: T)(implicit semigroup: Semigroup[T]): T = semigroup.combine(value, other)
}


implicit class MonoOps[T](value: T) {
  def anEmpty(implicit monoid: Monoid[T]): T = monoid.empty
}


implicit val monInt = new Monoid[Int] {
  override def empty: Int = 0
  override def combine(a: Int, b: Int): Int = intSemi.combine(a, b)
}

implicit val monStr = new Monoid[String] {
  override def empty: String = "____empty String ____"
  override def combine(a: String, b: String): String = strSemi.combine(a, b)
}


"foo" combineI "bar"
1 combineI 2

"foo".anEmpty
1.anEmpty

