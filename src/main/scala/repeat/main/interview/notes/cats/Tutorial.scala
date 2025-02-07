package repeat.main.interview.notes.cats

import akka.actor.TypedActor.dispatcher

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

      - моно морфизм -


            - эндо морфизм - морфизмы, в которых начало и конец совпадают, является моноидом
            - авто морфизм -

            - епи морфизм -
            - би морфизм — это морфизм, являющийся одновременно мономорфизмом и эпиморфизмом

      Cats is a library which provides abstractions for functional programming in the Scala programming language
      Cats goals -> support functional programming in Scala applications


      GENERAL FLOW FOR CATS:
        import cats.Eq
        import cats.syntax.eq._
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
      Example, modify every element of a collection without dropping or adding elements

      Laws:
        identity:               fa.map(x => x)    ==  fa
        composition:            fa.map(f).map(g)  ==  fa.map(x => f(x) andThen g(x))

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
    }


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
    Extract -> Transform -> Wrap

      Monad has two fundamental operation
          Wrap a value: class `CustomMonad` wrap `value`. In functional world it's named as a `pure` or `unit`
          Transform a value by the given function, in our case it's a `flatMap`: T => CustomMonad[S]

      Monad(x).flatMap(x => Monad(x))   == Monad(x)                                 right identity
      Monad(x).flatMap(f)               == f(x)                                     left identity

      Monad(x).flatMap(f).flatMap(g)    == Monad(x).flatMap(x => f(x).flatMap(g))   composition, associativity (ETW -> ETW -> ETW)

 */

  }






}
