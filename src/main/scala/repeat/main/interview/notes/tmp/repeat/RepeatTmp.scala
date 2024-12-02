package repeat.main.interview.notes.tmp.repeat

import scala.util.Try
import RepeatTmp._
import repeat.main.interview.notes.tmp.repeat.FunctorImplicits._
import repeat.main.interview.notes.tmp.repeat.FunctorInterface._

object FunctorImplicits {
  implicit object ListFunctor extends Functor[List] {
    override def map[A, B](container: List[A])(function: A => B): List[B] = container.map(function)
  }

  implicit object OptionFunctor extends Functor[Option] {
    override def map[A, B](container: Option[A])(function: A => B): Option[B] = container.map(function)
  }

  implicit object TryFunctor extends Functor[Try] {
    override def map[A, B](container: Try[A])(function: A => B): Try[B] = container.map(function)
  }
}


object RepeatTmp {
  def main(args: Array[String]): Unit = {
    println(x10(List(1,2,3)))
  }
}


trait Functor[F[_]] {
  def map[A, B](container: F[A])(function: A => B): F[B]
}




object FunctorInterface {
  def x10[C[_]](container: C[Int])(implicit functor: Functor[C]): C[Int] = functor.map(container)(_ + 10)

}
