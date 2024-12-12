package repeat.main.interview.notes.cats

import akka.actor.TypedActor.dispatcher

import scala.concurrent.{ExecutionContext, Future}
import cats.syntax.all._

import scala.collection.BuildFromLowPriority2
import scala.concurrent.ExecutionContext.Implicits.global


object Main {
  def main(args: Array[String]): Unit = {
    println("Hi there ...")

//    processAsync
//    processSync
    Thread.sleep(20000)

  }


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

    Functor
       - provides the ability for its values to be "mapped over"
       - function that transforms inside a value while remembering its shape
         Example, modify every element of a collection without dropping or adding elements

    Laws:
       identity:               fa.map(x => x)    ==  fa
       composition:            fa.map(f).map(g)  ==  fa.map(x => f(x) andThen g(x))

*/

  trait Functor[F[_]] {
    def map[A, B](f: A => B)(implicit functor: F[A]): F[B]
  }

  object FunctorImpl {
    implicit val option = new Functor[Option] {
      override def map[A, B](f: A => B)(implicit functor: Option[A]): Option[B] = functor.map(f)
    }

    implicit val list = new Functor[List] {
      override def map[A, B](f: A => B)(implicit functor: List[A]): List[B] = functor.map(f)
    }
  }


  /*
      Semigroup


   */








  /*
      convert:
          List[Future[String]] =>
          Future[List[String]]
  */
  def lists: List[Future[String]] = ???
  def convert: Future[List[String]] = lists.sequence


  /*
    Future[Option[Option[Int]]] =>
    Future[Option[Int]]
  */

  def f1: Option[Int] = ???
  def f2(v: Int): Future[Option[Int]] = ???
//  def f3: Future[Option[Option[Int]]] = f1.flatTraverse(f2)






}
