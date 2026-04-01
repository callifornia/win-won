package ______conspect______.repeat_tutorial


object Repeat_1 {

  def main(args: Array[String]): Unit = {

  }

  // FreeMonad
  trait Monad[M[_]] {
    def pure[A](value: A): M[A]
    def flatMap[A, B](m: M[A])(f: A => M[B]): M[B]
  }







}
