package repeat.main.scala

object ScalaTutorialWorkSheatObject {

  trait Tree[+T]
  object Tree {
    def leaf[T](value: T): Tree[T] = Leaf(value)
    def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
  }

  case class Leaf[+T](value: T) extends Tree[T]
  case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

  trait Functor[C[_]] {
    def map[A, B](container: C[A])(function: A => B): C[B]
  }


  object Functor {

    implicit object ListFunctor extends Functor[List] {
      override def map[A, B](container: List[A])(function: A => B): List[B] = container.map(function)
    }

    implicit object OptionFunctor extends Functor[Option] {
      override def map[A, B](container: Option[A])(function: A => B): Option[B] = container.map(function)
    }

    implicit object TreeFunctor extends Functor[Tree] {
      override def map[A, B](container: Tree[A])(function: A => B): Tree[B] = container match {
        case Leaf(value)                => Leaf(function(value))
        case Branch(value, left, right) => Branch(function(value), map(left)(function), map(right)(function))
      }
    }
  }

  def devx10[C[_]](container: C[Int])(implicit functor: Functor[C]) = {
    functor.map(container)(_ * 10)
  }







  def main(args: Array[String]): Unit = {

    // 3 - 1 - 2 - 4
    val list = DLLEnpty.prepend(1).append(2).prepend(3).append(4)
    assert(list.value == 1)
    assert(list.next.value == 2)
    assert(list.next.prev == list)
    assert(list.prev.value == 3)
    assert(list.prev.next == list)
    assert(list.next.next.value == 4)
    assert(list.next.next.prev.prev == list)
  }
}

