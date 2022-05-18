package functor

object Main {

  trait CustomFunctor[C[_]] {
    def map[A, B](container: C[A])(function: A => B): C[B]
  }

  trait Tree[+T]

  case class Leaf[+T] private (value: T) extends Tree[T]
  object Leaf {
    def apply[T](value: T): Leaf[T] = new Leaf(value)
  }
  case class Branch[+T] private (value: T, left: Tree[T], right: Tree[T]) extends Tree[T]
  object Branch {
    def apply[T](value: T, left: Tree[T], right: Tree[T]): Branch[T] = new Branch(value, left, right)
  }

  val tree: Tree[Int] =
    Branch(
      value = 1,
      left =
        Branch(
          value = 2,
          left =
            Branch(
              value = 3,
              left = Leaf(4),
              right = Leaf(5)),
          right = Leaf(6)),
      right = Leaf(7))


  @main def run(): Unit = {

    extension (str: String) {
      def a123(asd: String): String = asd
    }
//    println(tree.map(_ * 10))

    println("asd".a123("dddd"))

  }


}










