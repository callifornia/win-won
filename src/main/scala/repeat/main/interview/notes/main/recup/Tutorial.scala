package repeat.main.interview.notes.main.recup

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global



object Tutorial {


  //  Monad intro

  /*
  *   Monad it's a power full concept котре дозволяють покращити data structure with for comprehension
  *   
  * */
  /* Monad Option */
  import cats.Monad
//  import cats.instances.option._

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

  def listPair(numbers: List[Int], chars: List[Char]): List[(Int, Char)] = numbers.flatMap(x => chars.map(c => (x, c)))
  def optionPair(numbers: Option[Int], chars: Option[Char]): Option[(Int, Char)] = numbers.flatMap(x => chars.map(c => (x, c)))
  def futurePair(numbers: Future[Int], chars: Future[Char]): Future[(Int, Char)] = numbers.flatMap(x => chars.map(c => (x, c)))

  def calculate[M[_], A, B](m1: M[A], m2: M[B])(implicit v: Monad[M]): M[(A, B)] =
    Monad[M].flatMap(m1)(x => Monad[M].map(m2)(y => (x, y)))

  /*  right way   */
  def calculate2[M[_], A, B](m1: M[A], m2: M[B])(implicit monad: Monad[M]): M[(A, B)] =
    monad.flatMap(m1)(x => monad.map(m2)(y => (x, y)))



//  def main(args: Array[String]): Unit = {
//    println("====>" + calculate(List(1,2,3), List('a', 'b', 'c')))
//    println("====>" + calculate(Option(1), Option('a')))
//  }




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
