package repeat.main.interview.notes.poligon

import scala.util.Try

object Poligon2 {
  def main(args: Array[String]): Unit = {

    def combineList(n: List[Int], s: List[String]): List[(Int, String)] =
      for {
        n <- n
        s <- s
      } yield (n, s)

    def combineOption(n: Option[Int], s: Option[String]): Option[(Int, String)] =
      for {
        n <- n
        s <- s
      } yield (n, s)

    def combineTry(n: Try[Int], s: Try[String]): Try[(Int, String)] =
      for {
        n <- n
        s <- s
      } yield (n, s)


    trait Monad[M[_]] {
      def pure[A](value: A): M[A]
      def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
      def map[A, B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(a => pure(f(a)))
    }

//      monad.flatMap(n)(n => monad.map(s)(s => (n, s)))

    implicit val monadList = new Monad[List] {
      override def pure[A](value: A): List[A] = List(value)
      override def flatMap[A, B](ma: List[A])(f: A => List[B]): List[B] = ma.flatMap(f)
    }

    implicit val monadOption = new Monad[Option] {
      override def pure[A](value: A): Option[A] = Option(value)
      override def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] = ma.flatMap(f)
    }

    /* add map and flatMap into the M[_]*/
    implicit class MOps[M[_], A](m: M[A])(implicit monad: Monad[M]) {
      def map[B](f: A => B): M[B] = monad.map(m)(f)
      def flatMap[B](f: A => M[B]): M[B] = monad.flatMap(m)(f)
    }

    def combine_v2[M[_]: Monad, A, B](n: M[A], s: M[B]): M[(A, B)] =
      for {
        n <- n
        s <- s
      } yield (n, s)


    val r3 = combine_v2(List(1,2,3), List("a", "b", "c"))
    val r4 = combine_v2(Option(1), Option("a"))

    println("--> " + r3)
    println("--> " + r4)
  }
}
