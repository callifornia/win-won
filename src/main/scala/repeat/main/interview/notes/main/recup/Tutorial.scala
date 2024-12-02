package repeat.main.interview.notes.main.recup

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object Tutorial {


  //  Monad intro

  /* Monad Option */
  import cats.Monad
  import cats.instances.option._

  val anOptionMonad = Monad[Option]
  val anOptionValue = anOptionMonad.pure(123)
  val anOptionResult = anOptionMonad.flatMap(anOptionValue)(x => Some(x * 2))
  println(anOptionResult)


  /* Monad List */
  val aListMonad = Monad[List]
  val aListValue = aListMonad.pure(3)
  val aListResult = aListMonad.flatMap(aListValue)(x => List(x / 2))
  println(aListResult)


  /* Monad Future */
  val aFutureMonad = Monad[Future]
  val aFutureValue = aFutureMonad.pure(114)
  val aFutureResult = aFutureMonad.flatMap(aFutureValue)(x => Future(x * 2))
  aFutureResult.onComplete(println)












  //  Semigroup & Monoid

  /*  Type class itself declaration   */
  trait Semigroup[T] {
    def combine(a: T, b: T): T
  }

  /*  Interface   */
  object Semigroup {
    def apply[T](implicit sum: Semigroup[T]): Semigroup[T] = sum
  }

  object SemigroupMonoidSyntaxt {
    implicit class MonoidSyntaxtOps[A](value: A) {
      def |+|(other: A)(implicit sum: Semigroup[A]): A = sum.combine(value, other)
    }
  }



  /*  Type class itself declaration   */
  trait Monoid[T] extends Semigroup [T] {
    def empty: T
  }


  /*  Interface   */
  object Monoid {
    def apply[T](implicit m: Monoid[T]): Monoid[T] = m
  }



  object IntImplicits {
    implicit object IntSemigroup extends Semigroup[Int] {
      override def combine(a: Int, b: Int): Int = a + b
    }
  }


  object StringImplicits {
    implicit object MonoidString extends Monoid[String] {
      override def empty: String = ""
      override def combine(a: String, b: String): String = StringSemigroup.combine(a, b)
    }

    implicit object StringSemigroup extends Semigroup[String] {
      override def combine(a: String, b: String): String = a + b
    }
  }








  // Type classes variants
  object TypeClassesVariance {
    /*
    * works in this way:
    *       makeSound[Cat]      -> implicit object AnimalSoundMaker extends SoundMaker[Сat]
    *       makeSound[Animal]   -> implicit object AnimalSoundMaker extends SoundMaker[Animal]
    * because:
    *       trait SoundMaker[T]
    * where each type has it's own implicit implementation:
    *        implicit object AnimalSoundMaker extends SoundMaker[Animal]
    *        implicit object CatSoundMaker    extends SoundMaker[Cat]
    * */
    {
      trait Animal
      class Cat extends Animal

      trait SoundMaker[T]

      implicit object AnimalSoundMaker extends SoundMaker[Animal]
      implicit object CatSoundMaker extends SoundMaker[Cat]

      def makeSound[T](implicit soundMaker: SoundMaker[T]): Unit = println(soundMaker)

      makeSound[Cat]
      makeSound[Animal]
    }



    /*
     *  works in this way:
     *       makeSound[Animal]   -> implicit object AnimalSoundMaker extends SoundMaker[Animal]
     *       makeSound[Cat]      -> implicit object AnimalSoundMaker extends SoundMaker[Animal]
     *  because:
     *       trait SoundMaker[-T]
     *  and this one can be used for both variant:
     *       implicit object AnimalSoundMaker extends SoundMaker[Animal]
     * */
    {
      trait Animal
      class Cat extends Animal

      trait SoundMaker[-T]
      implicit object AnimalSoundMaker extends SoundMaker[Animal]

      def makeSound[T](implicit soundMaker: SoundMaker[T]): String = "hello"

      makeSound[Animal]
      makeSound[Cat]
    }


    /*
     *  works in this way:
     *       makeSound[Animal]   -> implicit object AnimalSoundMaker extends SoundMaker[Cat]
     *       makeSound[Cat]      -> implicit object AnimalSoundMaker extends SoundMaker[Cat]
     *  because:
     *       trait SoundMaker[-T]
     *  and this one can be used for both variant:
     *       implicit object AnimalSoundMaker extends SoundMaker[Animal]
     * */
    {

      trait Animal
      class Cat extends Animal

      trait SoundMaker[+T]
      implicit object AnimalSoundMaker extends SoundMaker[Cat]

      def makeSound[T](implicit soundMaker: SoundMaker[T]): Unit = ???

      makeSound[Animal]
      makeSound[Cat]
    }
  }
}
