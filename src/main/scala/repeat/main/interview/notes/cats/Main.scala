package repeat.main.interview.notes.cats

object Main {

  trait Functor[F[_]] {
    def map[A, B](container: F[A])(function: A => B): F[B]
  }


  object FunctorInstances {
    implicit object OptionFunctor extends Functor[Option] {
      override def map[A, B](container: Option[A])(function: A => B): Option[B] = container.map(function)
    }

    implicit object ListFunctor extends Functor[List] {
      override def map[A, B](container: List[A])(function: A => B): List[B] = container.map(function)
    }

    implicit object TreeFunctor extends Functor[Tree] {
      override def map[A, B](container: Tree[A])(function: A => B): Tree[B] =
        container match {
          case Leaf(value) => Leaf(function(value))
          case Branch(value, left, right) => Branch(function(value), map(left)(function), map(right)(function))
        }
    }
  }


  object FunctorSyntax {
    implicit class FunctorOps[A, F[_]](value: F[A]) {
      def map[B](function: A => B)(implicit functor: Functor[F]): F[B] = functor.map(value)(function)
    }
  }

  trait Tree[+T]

  object Tree {
    def leaf[T](value: T): Tree[T] = Leaf(value)

    def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
  }

  case class Leaf[+T](value: T) extends Tree[T]

  case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

  def main(args: Array[String]): Unit = {
    val tree = Tree.branch(1, Tree.leaf(2), Tree.branch(3, Tree.leaf(4), Tree.leaf(5)))

    import FunctorSyntax._
    import FunctorInstances._

    val mappedTree = tree.map(_ + 100)
    println(tree)
    println(mappedTree)
  }

}


