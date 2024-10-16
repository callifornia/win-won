package repeat.main.scala_main_concepts

object PoligonTutorial {


  trait Semigroup[T] {
    def combine(a: T, b: T): T
  }

  def combine[T](a: T, b: T)(implicit semigroup: Semigroup[T]) = semigroup.combine(a, b)

  object Semigroup {
    implicit val intSemigroup = new Semigroup[Int] {
      override def combine(a: Int, b: Int): Int = a + b
    }

    implicit val stringSemigroup = new Semigroup[String] {
      override def combine(a: String, b: String): String = a + b
    }

    implicit val listSemigroup = new Semigroup[List[Int]] {
      override def combine(a: List[Int], b: List[Int]): List[Int] = a.concat(b)
    }
  }

  implicit class IntOps[T](value: T) {
    def |+|(otherValue: T)(implicit semigroup: Semigroup[T]): T = semigroup.combine(value, otherValue)
  }

  def main(args: Array[String]): Unit = {
    val combined = 2 |+| 3
    val combinedOps = 4 |+| 5
  }
}
