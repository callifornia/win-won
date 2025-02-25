package repeat.main.interview.notes.cats

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


  //################################ Cats ################################
  /*
      Тео́рия катего́рий — раздел математики, изучающий свойства отношений между математическими объектами,
                         не зависящие от внутренней структуры объектов.

            - категория множеств
            - категория групп
            - категория модулей
            - категория векторных пространств

      морфизми -> стрелоки
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



            - эндо морфизм - морфизмы, в которых начало и конец совпадают, является моноидом
            - авто морфизм -


            - би морфизм — это морфизм, являющийся одновременно мономорфизмом и эпиморфизмом

      Cats is a library which provides abstractions for functional programming in the Scala programming language
      Cats goals -> support functional programming in Scala applications


      GENERAL FLOW FOR CATS:
        import cats.Eq
        import cats.instances.int._
        import cats.syntax.eq._

        import cats.implicits._
  */


  //  Type classes
  {
    /*
        - is a data structure which support ad hoc polymorphism
        - we can ADD NEW METHOD or NEW DATA without changing existing code

        There are three important point about type classes:
          - type class itself
          - instances for particular type
          - interface method (API) that we exposed to user
    */
    trait Convertor[T] {
      def convert(value: T): String
    }


    object ConvertorSyntax {
      implicit class ConvertorOps[A](value: A) {
        def convert()(implicit convertor: Convertor[A]): String = convertor.convert(value)
      }
    }


    object ConvertorInstances {
      implicit object IntSummup extends Convertor[Int] {
        override def convert(n: Int): String = n.toString
      }

      implicit object StrSummup extends Convertor[Boolean] {
        override def convert(s: Boolean): String = s.toString
      }
    }


    /*
      implicit class ConvertorOps[A](value: A) {
        def convert()(implicit converter: Convertor[A]): String = converter.convert(value)
      }

      The behavior of that method convert is 'ad-hoc' because of:

                          implicit convertor: Convertor[A]

      where in the code we have capability to call convertor.convert method  only when convertor is supplied.
      That is the part of ad-hoc

      ad-hoc - capability to call convert method on convertor only when convertor is supplied.

      implicit convertor: Convertor[T] <- ad-hoc
      [T]                              <- polymorphism, where for any specific type we must have it's own implementation

     */


    /* Using */
    import ConvertorSyntax._
    import ConvertorInstances._
    1.convert()
    true.convert()
  }




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




  // Semigroup
  {
    /*
    It's just a combine method which combines two values of the same type into the one:

                trait Semigroup[T] {
                  def combine(a: T, b: T): T
                }
    Law:
      associative:
          combine(a, combine(b, c)) === combine(combine(a, b), c)

  */


    trait Semigroup[T] {
      def combine(a: T, b: T): T
    }

    object SemigroupSyntax {
      implicit class SemigroupOps[A](value: A) {
        def |+|(v: A)(implicit semigroup: Semigroup[A]): A = semigroup.combine(value, v)
      }
    }


    object SemigroupInstances {
      implicit val intSemigroup = new Semigroup[Int] {
        override def combine(a: Int, b: Int): Int = a + b
      }

      implicit val strSemigroup = new Semigroup[String] {
        override def combine(a: String, b: String): String = a + b
      }
    }

    1 |+| 2
    "foo" |+| "bar"
  }



  // Monoid
  {
    /*
      It's just a trait which extends Semigroup and has only one method "empty"

              trait Monoid[A] extends Semigroup[A] {
                def empty(): A
              }

      law:
        associativity:
              empty + x === x, x + empty == x
    */

    trait Semigroup[T] {
      def combine(a: T, b: T): T
    }

    trait Monoid[A] extends Semigroup[A] {
      def empty(): A
    }


    object MonoidSyntax {
      implicit class MonoidOps[A](value: A) {
        def empty(implicit monoid: Monoid[A]): A = monoid.empty()
      }
    }


    object MonoidInstances {
      implicit object IntMonoid extends Monoid[Int] {
        override def empty(): Int = 0

        override def combine(a: Int, b: Int): Int = a + b
      }

      implicit object StrMonoid extends Monoid[String] {
        override def empty(): String = ""

        override def combine(a: String, b: String): String = a + b
      }
    }

    import MonoidSyntax._
    import MonoidInstances._

    "foo".empty
    2.empty
  }






  // Functor
  {

    /*
      Functor
        - provides the ability for its values to be "mapped over"
        - function that transforms inside a value while remembering its shape
        - fundamental method is a "map"
      Example, modify every element of a collection without dropping or adding elements

      Laws:
        identity:               fa.map(x => x)    ==  fa
        composition:            fa.map(f).map(g)  ==  fa.map(x => f(x) andThen g(x))


      In mathematical concept:
        Functor - defines transformations between categories. In Scala - defines transformations between Scala types
        EndoFunctors - defines transformations between same categories


      Functor natural transformation is about:
                  List => Option

      as an example:
        trait FunctorTrans[-F[_], +G[_]] {
          def apply[A](v: F[A]): G[A]
        }

        object FunctorTrans extends FunctorTrans[List, Option] {
          def apply[A](v: List[A]): Option[A] = v.headOption
        }

      all the next methods work in that way which are basically transformations between Functors:
        .toList, .toSeq, .toOption, .toEither, .toTry and so on ...

    */


    trait Functor[F[_]] {
      def map[A, B](container: F[A])(function: A => B): F[B]
    }


    object FunctorInstances {
      implicit object optionFunctor extends Functor[Option] {
        override def map[A, B](container: Option[A])(function: A => B): Option[B] = container.map(function)
      }

      implicit object listFunctor extends Functor[List] {
        override def map[A, B](container: List[A])(function: A => B): List[B] = container.map(function)
      }

      implicit object treeFunctor extends Functor[Tree] {
        override def map[A, B](container: Tree[A])(function: A => B): Tree[B] =
          container match {
            case Leaf(value)                => Leaf(function(value))
            case Branch(value, left, right) => Branch(function(value), map(left)(function), map(right)(function))
        }
      }

      type Id[A] = A
      implicit object idFunctor extends Functor[Id] {
        def map[A, B](container: A)(function: A => B): B = function.apply(container)
      }
    }



    // small cats magic

    object FunctorSyntax{
      implicit class FunctorOps[A, F[_]](value: F[A]) {
        def map[B](function: A => B)(implicit functor: Functor[F]): F[B] = functor.map(value)(function)
      }
    }

    trait Tree[+T]
    object Tree {
      def leaf[T](value: T): Tree[T] = Leaf(value)
      def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
    }

    case class Leaf[+T](value: T) extends Tree[T]
    case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

    val tree = Tree.branch(1, Tree.leaf(2), Tree.branch(3, Tree.leaf(4), Tree.leaf(5)))

    import FunctorSyntax._
    import FunctorInstances._

    tree.map(_ + 100)
  }


  // Monad
  {
    /*
    Monad -> data structure with sequential capabilities
    Monad a higher kinder type which provide ability to transform values in a chain way:
      - to do some calculations on those things which are inside
      - and in the end have the same type
      - extends a Functor in cats lib
      - to be able write the for-comprehension we do need to have "map" and "flatMap"


    - for-comprehensions transform by compiler into the "map" and "flatMap"


    Extract -> Transform -> Wrap

      Monad has two fundamental operation
          Wrap a value: class `CustomMonad` wrap `value`. In functional world it's named as a `pure` or `unit`
          Transform a value by the given function, in our case it's a `flatMap`: T => CustomMonad[S]

      Monad(x).flatMap(x => Monad(x))   == Monad(x)                                 right identity
      Monad(x).flatMap(f)               == f(x)                                     left identity

      Monad(x).flatMap(f).flatMap(g)    == Monad(x).flatMap(x => f(x).flatMap(g))   composition, associativity (ETW -> ETW -> ETW)



                  List(x).flatMap(f)        ==  f(x)
                  xs.flatMap(x => List(x))  ==  xs
                  xs.flatMap(f).flatMap(g)  ==  xs.flatMap(x => f(x).flatMap(g))



    */

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
  


}




