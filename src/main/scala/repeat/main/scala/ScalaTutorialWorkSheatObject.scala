package repeat.main.scala

object ScalaTutorialWorkSheatObject {


  abstract class MyList[+T] {
    def head: T
    def tail: MyList[T]
  }

  case object EmptyList extends MyList[Nothing] {
    override def head: Nothing = throw new NullPointerException
    override def tail: MyList[Nothing] = throw new NullPointerException
  }

  class NonEmptyList[T](h: => T, t: MyList[T]) extends MyList[T] {
    override lazy val head: T = {
      println("Head is evaluated: " + h)
      h
    }
    override lazy val tail: MyList[T] = {
      println("Tail is evaluated: " + h)
      t
    }
  }


  def main(args: Array[String]): Unit = {
    val list = new NonEmptyList[Int](1, new NonEmptyList[Int](2, new NonEmptyList[Int](3, new NonEmptyList[Int](4, EmptyList))))
    list.head
    list.tail.tail.head

  }
}
