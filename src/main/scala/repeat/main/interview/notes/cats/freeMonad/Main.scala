package repeat.main.interview.notes.cats.freeMonad


object Main {

  def main(args: Array[String]): Unit = {
    val result =
      for {
        foo   <- Exist("foo")
        _     <- Empty
        bar   <- Exist("bar")
        _     <- Empty
      } yield foo + "-" + bar

    val a = List.empty[String]
    println(s"Result is: $result")
  }


  trait MyContainer[+A] {
    def pure[A](value: A): MyContainer[A] = Exist(value)
    def flatMap[B](f: A => MyContainer[B]): MyContainer[B] = {
      this match {
        case Exist(value) => f(value)
        case Empty => Empty
      }
    }

    def map[B](f: A => B): MyContainer[B] = flatMap(v => pure(f(v)))
  }

  case class Exist[A](value: A) extends MyContainer[A]
  case object Empty extends MyContainer[Nothing]
}
