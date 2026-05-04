package ______conspect______.cats

import akka.actor.TypedActor.dispatcher
import cats.data.{State, Validated}

import scala.concurrent.{ExecutionContext, Future}
import cats.syntax.all._

import scala.collection.BuildFromLowPriority2
import scala.concurrent.ExecutionContext.Implicits.global


object Tutorial {
  def main(args: Array[String]): Unit = {
    //    println(CustomSemigroup.check())
  }



  // call-by need pattern
  {
    /*
     This pattern is about lazy evaluations where class parameters are declared as call-by name and values marked as a
     lazy val ... Example can be find bellow (linked list)
  */

    trait DLLList[+T] {
      def value: T
      def next: DLLList[T]
      def prev: DLLList[T]
      def append[S >: T](element: S): DLLList[S]
      def prepend[S >: T](element: S): DLLList[S]
      def updateNext[S >: T](newNext: => DLLList[S]): DLLList[S]
      def updatePrev[S >: T](newPrev: => DLLList[S]): DLLList[S]
    }


    object DLLEmpty extends DLLList[Nothing] {
      override def value: Nothing = throw new NoSuchElementException("There are no element")
      override def next: DLLList[Nothing] = throw new NoSuchElementException("There are no element")
      override def prev: DLLList[Nothing] = throw new NoSuchElementException("There are no element")
      override def append[S >: Nothing](element: S) = new DLLCons(element, DLLEmpty, DLLEmpty)
      override def prepend[S >: Nothing](element: S) = new DLLCons(element, DLLEmpty, DLLEmpty)
      override def updateNext[S >: Nothing](newNext: => DLLList[S]) = this
      override def updatePrev[S >: Nothing](newPrev: => DLLList[S]) = this
    }


    class DLLCons[+T](override val value: T,
                      p: => DLLList[T],
                      n: => DLLList[T]) extends DLLList[T] {

      override lazy val next: DLLList[T] = n
      override lazy val prev: DLLList[T] = p

      override def updatePrev[S >: T](newPrev: => DLLList[S]): DLLList[S] = {
        lazy val result: DLLList[S] = new DLLCons(value, newPrev, n.updatePrev(result))
        result
      }

      override def updateNext[S >: T](newNext: => DLLList[S]): DLLList[S] = {
        lazy val result: DLLList[S] = new DLLCons(value, p.updateNext(result), newNext)
        result
      }

      override def append[S >: T](element: S): DLLList[S] = {
        lazy val result: DLLList[S] = new DLLCons(value, p.updateNext(result), n.append(element).updatePrev(result))
        result
      }

      override def prepend[S >: T](element: S): DLLList[S] = {
        lazy val result: DLLList[S] = new DLLCons(value, p.prepend(element).updateNext(result), n.updatePrev(result))
        result
      }

      val list = DLLEmpty.prepend(1).append(2).prepend(3).append(4)
      assert(list.value == 1)
      assert(list.next.value == 2)
      assert(list.next.prev == list)
      assert(list.prev.value == 3)
      assert(list.prev.next == list)
      assert(list.next.next.value == 4)
      assert(list.next.next.prev.prev == list)
    }
  }




  // companion object
  /*  class + object = companion  */
  class Kid {
    val _ = Kid.privateField
  }

  object Kid { private val privateField = 123 }


/*

      ################################ Cats ################################################################

      Cats       →  is a library which provides abstractions for functional programming in the Scala programming language
      Cats goals →  support functional programming in Scala applications
      GENERAL FLOW FOR CATS:
          import cats.Eq
          import cats.instances.int._
          import cats.syntax.eq._

          import cats.implicits._


      Тео́рия катего́рий — раздел математики, изучающий свойства отношений между математическими объектами,
                         не зависящие от внутренней структуры объектов.

            - категория множеств
            - категория групп
            - категория модулей
            - категория векторных пространств

      морфизми                → стрелоки
      Коммутативная диаграмма — это ориентированный граф, в вершинах которого находятся объекты, а стрелками являются морфизмы

      - изо морфизм (https://ru.hexlet.io/courses/graphs/lessons/isomorphism/theory_unit):
        изоморфизм -  буквально означает «одинаковая форма». Два графа изоморфны, если это один и тот же граф,
        просто нарисованный или представленный по-другому. Другими словами, два графа считаются изоморфными,
        если мы можем идеально сопоставить вершины одного графа с вершинами другого. При этом смежность тоже должна совпадать:
          - если две вершины были смежными в первом графе, во втором они тоже должны быть смежными
          - если две вершины не были смежными в первом графе, во втором они тоже не должны быть смежными


      - моно морфизм - стрелки которие можно сократить слева

             h ->                 g: A -> B        f * h = f * g
          A        B  f ->  C     h: A -> B            h = g
             g ->                 f: B -> C


      - епи морфизм - стрелки которие можно сократить вправа

                        h ->        h: B -> C      h * f = g * f
          A   f ->  B          C    g: B -> C          h = g
                        g ->        f: A -> B


      - эндо морфизм(моноид) - морфизмы, в которых начало и конец совпадают, является моноидом
      - би морфизм — это морфизм, являющийся одновременно мономорфизмом и эпиморфизмом.
                     функція, яку можна повністю “перевернути назад” без втрат.
      - авто морфизм - ???
  */


  /*

  */






  // Eq
  {
    /*
      Using to compare values by it's type
      Example:
          val result = 2 == "foo"    - compile and return false

      Using Eq
          val result = 2 === "foo"   - not compile

      Extend with a custom type:
        import cats.Eq
        import cats.syntax.eq._

      case class Foo(name: String, age: Int)
      implicit val fooEq: Eq[Foo] = Eq.instance[Foo] { (foo1, foo2) => foo1.age === foo2.age }
    */

    import cats.Eq
    import cats.syntax.eq._

    case class Foo(name: String, age: Int)
    implicit val fooEq: Eq[Foo] = Eq.instance[Foo] { (foo1, foo2) => foo1.age === foo2.age }

    Foo("a", 1) === Foo("b", 1)
  }



  // Free Monad
  /*
  regular Monad:
    trait Monad[M[_]] {
      def pure[A](value: A): M[A]
      def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
    }

  free Monad:
  trait Free[M[_], A] {
    def pure(v: A): Free[M, A]
    def flatMap[B](f: A => Free[M, B]): Free[M, B]
  }
  * */





  // Validated
  /*
     Let say we do have a bunch of conditions to check
       - return error list with all possible errors
       - in case no errors return an int
   * */

  // regular solutions
  def testNumber(n: Int): Either[List[String], Int] =
    List(
      (x: Int) => if (x < 120) Right(x)   else Left(s"$n - must be less than 120"),
      (x: Int) => if (x > 0)   Right(x)   else Left(s"$n - must be non negative"),
      (x: Int) => if (x < 100) Right(x)   else Left(s"$n - must be <= 100"))
      .map(_.apply(n))
      .foldLeft(Right.apply[List[String], Int](n): Either[List[String], Int]) { (acc, el) =>
        el match {
          case Right(_)  => acc
          case Left(str) => acc match {
            case Right(_)     => Left.apply[List[String], Int](str :: Nil)
            case Left(errors) => Left.apply[List[String], Int](errors :+ str)
          }
        }
      }



  // cats solutions
  def validateNumber(x: Int): Validated[List[String], Int] =
    Validated.cond(x < 120, x, List(s"$x - must be less than 120"))
      .combine(Validated.cond(x > 0, x, List(s"$x - must be non negative")))
      .combine(Validated.cond(x < 100, x, List(s"$x - must be <= 100")))


  println(testNumber(121))
  println(validateNumber(121))

  /*
    Output:
        Left(   List(121 - must be less than 120, 121 - must be <= 100))
        Invalid(List(121 - must be less than 120, 121 - must be <= 100))
   */




  // State
  /*
     State - is a structure that provides a functional approach to handling application state
     State[S, A]   -   S => (S, A)
      S - type that represents your state
      A - is the result the function produces
  * */

  /*    just a container    */
  case class Seed(value: Int) {
    def next(): Seed = Seed(value + 1)
  }


  /*    state representation  */
  val nextSeed: State[Seed, Int] = State { seed: Seed =>
    val newState = seed.next()
    (newState, newState.value)
  }


  println(
    nextSeed          /*  (Seed(1), 1)    */
      .map(_ * 10)    /*  (Seed(1), 10)   */
      .map(_ + 20)    /*  (Seed(1), 30)   */
      .run(Seed(0))
      .value)         /*  (Seed(1), 30)   */


  println(
    (for {
      a <- nextSeed   /*   a = 1   */
      b <- nextSeed   /*   b = 2   */
      c <- nextSeed   /*   c = 3   */
    } yield (a + b + c) * 10)
      .run(Seed(0))
      .value)         /*  Seed(3, 60)  */




  // Reader Monad

  /* Reader monad - ability to chain computation with an ability to set in apply method start point.
     Start point can be anything as a simple number or interface to use:
      - which encapsulate just a function
      - map and flatMap - for comprehension
    */
  case class Reader[A, B](run: A => B) {
    def apply(x: A): B = run(x)
    def map[C](f: B => C): Reader[A, C] = Reader[A, C](x => run.andThen(f).apply(x))
    def flatMap[C](f: B => Reader[A, C]): Reader[A, C] = Reader[A, C](x => map(f).apply(x).apply(x))
  }

  /* some logic which can be encapsulated into the Reader monad */
  trait UserRepository {
    def create(x: String): Option[Boolean]         = Some(true)
    def update(x: String): Either[String, Boolean] = Right(true)
    def delete(x: String): Either[String, Int]     = Right(3)
  }

  /* example of using Reader monad encapsulation */
  val result =
    for {
      created <- Reader[UserRepository, Option[Boolean]](_.create("foo"))
      updated <- Reader[UserRepository, Either[String, Boolean]](_.update("bar"))
      deleted <- Reader[UserRepository, Either[String, Int]](_.delete("bar"))
    } yield (created, updated, deleted)

  /* running by using */
  result.apply(new UserRepository {})


  /* another way to use that with an identity function */
  val userRepository = Reader[UserRepository, UserRepository](identity)
  val result2 =
    for {
      a <- userRepository.map(_.create("foo"))
      b <- userRepository.map(_.update("bar"))
      c <- userRepository.map(_.delete("baz"))
    } yield (a, b, c)

  result2.apply(new UserRepository {})




  // Writer Monad

   /* The Writer monad represents a computation that produces a value along with a description of the computation */
  import cats.data.Writer
  val writer = Writer("Scala", 1 + 2)

  println(writer.value)           // 3
  println(writer.tell(" Foo"))    // (Scala Foo, 3)
  println(writer.map(_ + 10).run) // (Scala, 13)
  println(writer.swap)            // (3, Scala)

  val (log, r) = writer.run
  println(log)                    // Scala
  println(r)                      // 3
}

