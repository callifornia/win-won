package repeat.main.interview.notes.interview

object InterviewStuff {
  def main(args: Array[String]): Unit = {
    println("Hello world./.")
  }
}

import scala.language.higherKinds

sealed trait Maybe[+A]
case object Nothing extends Maybe[Nothing]
case class Just[A](a: A) extends Maybe[A]


trait Functor[F[_]] {
  def map[A, B](fa: F[A])(fx: A => B): F[B]
}


object instances {
  class MaybeFunctor extends Functor[Maybe] {
    override def map[A, B](fa: Maybe[A])(fx: A => B): Maybe[B] =
      fa match {
        case Just(v) => Just(fx(v))
        case Nothing => Nothing
      }
  }

  implicit val maybeFunctor: Functor[Maybe] = new MaybeFunctor
}



object sintax {
  case class FunctorOps[F[_], A](fa: F[A]) {
    def map[B](fx: A => B)(implicit functor: Functor[F]): F[B] = functor.map(fa)(fx)
  }

  implicit def toFunctorOps[F[_], A](fa: F[A]): FunctorOps[F, A] = FunctorOps(fa)
}

import instances._
import sintax._

val maybe: Maybe[Int] = Just(5)
println(maybe.map(value => value * 2))
