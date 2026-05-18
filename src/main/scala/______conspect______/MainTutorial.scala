package ______conspect______

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/*
*   TODO:
*     Functional programing
*
*       Generic - https://www.youtube.com/watch?v=ozcY_K-ij20&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=52
*       Meta    - https://www.youtube.com/watch?v=m3Qh-MmWpbM&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=51
*                 https://www.youtube.com/watch?v=Y5rPHZaUakg&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=45
* */


object MainTutorial {
  def main(args: Array[String]): Unit = {
    println("Is everything ok ???????")
  }


//  ✅✅✅✅✅✅  // Functional Programing  ✅✅✅✅✅✅
  /*
    https://doc.akka.io/libraries/akka-core/2.6/typed/guide/actors-motivation.html#the-challenge-of-encapsulation

    В об’єктно-орієнтованих мовах ми рідко замислюємося про потоки виконання або лінійні шляхи виконання загалом.
    Ми часто уявляємо систему як мережу екземплярів об’єктів, які реагують на виклики методів, змінюють свій внутрішній стан,
    а потім взаємодіють один з одним через виклики методів, просуваючи вперед стан усього застосунку:

        • CPU записують дані в рядки кешу (cache lines), а не безпосередньо в пам’ять
        • передача рядків кешу між ядрами є дуже дорогою операцією
        • більшість цих кешів є локальними для ядра CPU
        • записи, зроблені одним ядром, не є видимими для іншого ядра
        • рядок кешу потрібно передати до кешу іншого ядра
        • у JVM для спільного використання між потоками застосовують volatile або обгортки Atomic
        • важлива відмінність між передаванням повідомлень і викликом методів полягає в тому, що повідомлення не мають значення, яке повертається
        • actor-система може обробляти стільки повідомлень одночасно, скільки підтримує апаратне забезпечення

        • functional programming        work with a function as we work with a value
        • function value                can be passed around or return as a result

        • pattern matching              decomposition of an element
        • partial function              in other words it's pattern matching

        • higher kinder types           when one type accept another type
        • higher order function         when one function accept another function
        • inheritance model             extends 1 class but lots of traits
        • instruction vs expression

        • Expression                    produces a value        →   2 + 3, x * 10
        • Instruction (statement)       performs an action      →   let a = 10

        • method notation example:      1 + 2
        • scala vision                  ми розглядаємо програму як один вираз (expression), який обчислюється та повертає значення,
                                        що може бути складним, наприклад серверним застосунком.
                                        Потрібно мислити категоріями виразів, а не інструкцій (імперативний підхід)
        • associativity                 a x (b x c) = (a x b) x c
        • infix notation                ability to use method without any dots or parentheses, fundamental feature in language
        • auxiliary constructors        overloaded constructor this()
                                        must first call the primary constructor or another auxiliary constructor
        • lifting                       when we pass some method as if it were a function, Scala automatically converts it
                                        to a function object
        • scala prefixes                all the parameters with val
        • equals/hashCode               implemented for you based on the given parameters
        • companion object              created with the appropriate apply method, which takes the same arguments as declared in the case class
        • unapply                       allow the class name to be used as an extractor for pattern matching
        • sealed trait                  all the classes need to be in the same source file
        • first-class citizens          we can use functions as values or like normal variables
        • default scala imports         implicitly: java.lang._, scala._, scala.Predef._
        • linearization                 This is an important concept in Scala that explains how Scala resolves methods when multiple traits are mixed
                                        together. When a method is called, Scala looks for the implementation from left to right in this order
                                        This solves the diamond problem found in multiple inheritance
        • eta-expansion                 перетворення метода у функцію
                                            val function = someMethod _
                                            List(1,2,3,4).map(someMethod)
        • function                      is an instance of a function traits family: example: new Function[Int, Int]
        • method                        depends on a class or object where it defined where function is a plain object
        • referential transparency      це властивість виразу, коли його можна замінити на його значення без зміни поведінки програми
                    def add(a: Int, b: Int): Int = a + b
                    val five   = add(2, 3)
                    val _      = five + five
                    val ten_v1 = add(2, 3) + add(2, 3)
                    val ten_v2 = 5 + add(2, 3)

                    /* referential transparency is not */
                    def showTime(): Long = System.currentTimeMillis()
        • карирование                   перетворення функції з багатьма параметрами на ланцюжок функцій, де кожна функція приймає лише один параметр
                                        def add(a: Int, b: Int): Int      ~>      def add(a: Int)(b: Int): Int
        • substitution model:
             sumOfSquares(3, 2 + 2)
             sumOfSquares(3, 4)
             square(3) + square(4)
             3 * 3 + square(4)
             9 + square(4)
             9 + 4 * 4
             9 + 16
             25

      • implicit lookup:
             Look in current scope
               • implicit defined in current scope
               • imports explicit, wildcard
             Look at associated types in
               • companion object of a type
               • companion object OtherClass
               • companion object B
               • any superclass of B

      • Value Class
            • спеціальний клас-обгортка над одним значенням, який дозволяє додати типобезпечність і методи без створення додаткового
              об’єкта в пам’яті (у більшості випадків)
            • успадковується від AnyVal
            • class UserId(val value: String) extends AnyVal

            • меншого використання пам’яті
            • type safety без runtime cost
            • меншого GC pressure

      • call by name
            • def byName(x: => Long): Unit
            • going to be evaluated when it's going to be used inside that function "byName"
            • call by name also used in a Future which allows to execute some functionality in some time in some thread
            • Try as an example also implemented in such way: Try(throw new NullPointerExceptions)

      • right associative method | because method in class MyClass_2 ended with a ":"
                        class MyClass_2 {
                            def ::(a: Int): Unit   = ???
                            def ---:(b: Int): Unit = ???
                        }
                        val myClass = new MyClass_2
                        123 :: myClass
                        123 ---: myClass

      • identity

                        def identity[A](x: A): A = x
                        val a = identity(5)        // Int
                        val b = identity("hello")  // String

   */



//  ✅✅✅✅✅✅   unapply   ✅✅✅✅✅✅
  {
    case class Pet(age: Int, name: String)
    // unapply:     Pet ~> Option[Int, String]
    object Pet {
      def unapply(pet: Pet): Option[(Int, String)] =
        pet.age > 10 match {
          case true  => Some(10, "...")
          case false => Some(12, "...")
        }
      def unapply(value: Int): Option[String] =
        value > 12 match {
          case true => Some("Zero")
          case false => Some("One")
        }
    }

    Pet(13, "Billy") match {
      case Pet(age, status) => println(age, status) /* (10,...) */
    }

    Pet(13, "Billy").age match {
      case Pet(status) => println(status) /* Zero */
    }
  }


//  ✅✅✅✅✅✅   call by name infinity list  ✅✅✅✅✅✅
  {

    abstract class MyList1[+T] {
      def head: T
      def tail: MyList1[T]
    }

    case object EmptyList extends MyList1[Nothing] {
      override def head: Nothing          = throw new NullPointerException
      override def tail: MyList1[Nothing] = throw new NullPointerException
    }

    class NonEmptyList[T](h: => T, t: MyList1[T]) extends MyList1[T] {
      override lazy val head: T = {
        println("Head is evaluated: " + h)
        h
      }
      override lazy val tail: MyList1[T] = {
        println("Tail is evaluated: " + h)
        t
      }
    }

    val list = new NonEmptyList[Int](1, new NonEmptyList[Int](2, new NonEmptyList[Int](3, new NonEmptyList[Int](4, EmptyList))))
    list.head
    list.tail.tail.head
  }




//  ✅✅✅✅✅✅  variants | covariant | contravariant   ✅✅✅✅✅✅
  {
    /*
       Dog is subtype of Animal
       Is List[Dog] is subtype of List[Animal] = this is variant question

       In case answer is YES           it means Covariant | In Scala it marked as List[+A] */
            class Animal
            class Dog extends Animal
            val _: List[Animal] = List.empty[Dog]

    /*   In case answer is NO           it means Invariant where no relationships between List[Animal] and List[Dog]
                                        In Scala it marked as List[A] */
            class MyList[A]
            val _: MyList[Animal] = new MyList[Animal]

    /*   In case answer is HELL NO     it means Contravariant. Where in Scala it marked as List[-A] */
            class MyList_2[-A]
            val _: MyList_2[Dog] = new MyList_2[Animal]

    /* example */
    trait Vet[-T] {
      def heal(animal: T): Boolean
    }

    val myDogVet: Vet[Dog] = new Vet[Animal] {
      override def heal(animal: Animal): Boolean = {
        println("Dog is healthy")
        true
      }
    }

    myDogVet.heal(new Dog)

    /*
       Summary
        - In case your generic type produce(creates) or contain an elements   →  COVARIANT
        - In case your generic type "acts on" or consume an elements          →  CONTRAVARIANT
   */

  }



//  ✅✅✅✅✅✅  null, Null, Nothing, None  ✅✅✅✅✅✅
  {
    /*
         • null       як в Java
         • Null       можливе тільки значення null
                      не має методів
                      полів
         • Nothing    підтип усіх типів, NullPointerException, ???

                      def someFunction(a: Nothing): Int      = ???
                      def someFunction2(b: Nothing): Nothing = throw new NullPointerException

                      /* use in covariant side */
                      abstract class MyList2[+T]
                      class MyListSpec[T] extends MyList2[T]
                      object MyListSpecEmpty extends MyList2[Nothing]

                                                        Any
                                                       /   \
                                                 AnyVal     AnyRef
                                                    |        |
                                            Int, Double     String,
                                            Boolean...      List, User classes...
                                               \             |
                                               Null
                                                   \        |
                                                      Nothing
     */
  }



//  ✅✅✅✅✅✅   abstract class vs trait   ✅✅✅✅✅✅
   /*
     • trait               для поведінки, mixin, добавляє можливість
     • abstract class      для базового стану й ієрархії,

     • можуть мати abstract fields/methods
     • можуть мати non-abstract fields/methods

     • можуть наслідуватися від SINGLE abstract class
     • можуть наслідуватися від MULTIPLE traits
     • abstract class може приймати аргументи в конструкторі
     • trait не має конструктора
     • note: представляти сущність як abstract class
     • note: представляти поведінку як traits
  */

  {
    abstract class Person {
      def canFly: Boolean = true
      val canDrive: Boolean
      def discussWith(p: Person): String
    }

    trait PersonTrait {
      def canFly: Boolean = true
      val canDrive: Boolean
      def discussWith(p: Person): String
    }
  }




//   ✅✅✅✅✅✅   Blocking, Non-blocking  |  Sync, Async   ✅✅✅✅✅✅

  /*
      • Синхронний (контекст обєктів)   - якщо викликаюча сторона не може виконуватися, доки метод не поверне значення або не викличе виняток
      • Асинхронний (контекст обєктів)  - дозволяє викликаючій стороні виконуватися

      • Блокування(контекст потоків)    - якщо затримка одного потоку може на невизначений термін затримувати деякі інші потоки
      • Неблокування(контекст потоків)  - якщо потоки не очікують завершення інших потоків
  * */
  {
       // Синхронний  |  Блокування
          def check() = 1 + 2
          check()
          println("Foo")            //  не виконається доки check() не виконується та не поверне результат


      // Асинхронний  |  Неблокування
          def check2(): Future[Int] = Future(1 + 2)
          check2()
          println("Foo")            //  виконається зразу і не буде чекати поки check2 виконається
  }




  // Types system
  {
    val anInt: Int = 123        /*  level - 0 type */
    class MyAwesomeList[T]      /*  level - 1 type (type constructor) */
    class Function[F[_]]        /*  level - 2 type */
    class Meta[F[_[_]]]         /*  level - 3 type */
  }


  /*
    ✅✅✅✅✅✅    ADT (algebra data type)   ✅✅✅✅✅✅
      SUM       →  object
      Product   →  case class
      Hybrid    →  case class contains object Product(SUM)

    Algebra data type perform operations which exists, example:
      case class Create[A](key: String, value: A)
      case class Read(key: String)
      case class Update[A](key: String, value: A)
      case class Delete(key: String)
  * */

  {
      /*  SUM type  */
      sealed trait Weather
      case object Sunny extends Weather
      case object Rainy extends Weather
      case object Cloudy extends Weather
      case object Windy extends Weather

      /*  Product type  */
      case class Product(name: Name, email: Email)
      case class Email(value: String)
      case class Name(value: String)

      /*  Hybrid type */
      sealed trait WeatherForeCastResponse
      case class Valid(weather: Weather) extends WeatherForeCastResponse
      case class Invalid(error: String, description: String) extends WeatherForeCastResponse

    /*

        1. Illegal states are not representable
                def check(weather: Weather): Unit = ??? <- so we can not put anything else than Weather
        2. highly composable
        3. immutable data structure
        4. just data, not functionality => structure our code

     */
  }



  /*
    ✅✅✅✅✅✅✅✅✅✅✅✅    OOP     ✅✅✅✅✅✅✅✅✅✅✅✅

    • Поліморфізм —  можливість працювати з різними типами даних через один і той самий інтерфейс або ім’я методу

                                       trait Animal {
                                         def sound(): String
                                       }

                  class Dog extends Animal {                  class Cat extends Animal {
                    def sound() = "Woof"                        def sound() = "Meow"
                  }                                           }

          def makeSound(animal: Animal): Unit = {
            println(animal.sound())
          }

          makeSound(new Dog), makeSound(new Cat)
      • Одна функція makeSound працює з різними типами Dog, Cat


    • F-bound поліморфізм
        • техніка типізації, коли тип обмежує сам себе
        • клас повинен працювати лише зі своїм власним підтипом
        • основна проблема — повернення “правильного” типу при наслідуванні
        • приклад
              без:
                  trait Animal {
                    def mate(other: Animal): Animal
                  }
                  class Dog extends Animal
                  class Cat extends Animal

                  dog.mate(cat) ~> бред
              f-bound:
                  trait Animal[A <: Animal[A]] {
                    def mate(other: A): A
                  }
                  class Dog extends Animal[Dog]
                  class Cat extends Animal[Cat]

                  dog.mate(cat) ~> бред не компілиться
                  dog.mate(dog) ~> компілиться
  * */




  /*
    ✅✅✅✅✅✅✅✅✅✅✅✅    Patterns     ✅✅✅✅✅✅✅✅✅✅✅✅



   SOLID:
     Single responsibility  - каждий клас должен иметь одну и только одну причину для изменения
     Open closed            - открит для розширения, закрит для изменения (только дописивать)
     Liskov substitution    - функции которие используют базовий тип должни без изменения использовать
                              подтип
     Interface segregation  - много интерфейсов лучше чем один большой
     Dependency inversion   - зависимость на абстракции нет завимостей на конкретное

* */




  // custom while
  {
    def customWhile(bool: => Boolean)(function: => Unit): Unit =
      bool match {
        case true =>
          function
          customWhile(bool)(function)
        case false => ()
      }

    var i = 3
    customWhile(i > 0) {
      println("Hello world")
      i = i - 1
    }
  }


  /*  Some interesting point around initialization  */
  App.foo                     // in run we are "OK"
  App.foo.bar.someMethod()    // in run "NullPointerException" тому що
  // val foo = Foo(bar, baz) йде перед
  // val bar = Bar()

  case class Bar() { def someMethod(): String = "hi there ..." }
  case class Baz()
  case class Foo(bar: Bar, baz: Baz)

  object App {
    val foo = Foo(bar, baz)
    val bar = Bar()
    val baz = Baz()
  }




  //  ✅✅✅✅✅✅ // self type ✅✅✅✅✅✅
  {
    /*  Which mean everyone who extends Hospital MUST extends Builder  */
    trait Builder {
      def build(v: String): Boolean = true
    }

    trait Hospital {
      selfType: Builder =>
      def makeNewHospital(value: Int): Boolean = selfType.build(value.toString)
    }

    class MakeNewStructure extends Hospital with Builder {
      def someAwesomeMethod(): Unit = makeNewHospital(3)
    }
  }


  




//  ✅✅✅✅✅✅ // Type classes ✅✅✅✅✅✅
  {
    /*
      Type classes
          - структура даних яка підтримує ad hoc polymorphism
          - ми можемо додати новий метод чи нові дані без зміни існуючого кода

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
      implicit object IntSumUp extends Convertor[Int] {
        override def convert(n: Int): String = n.toString
      }

      implicit object StrSumUp extends Convertor[Boolean] {
        override def convert(s: Boolean): String = s.toString
      }
    }


    /*
      implicit class ConvertorOps[A](value: A) {
        def convert()(implicit converter: Convertor[A]): String = converter.convert(value)
      }

      The behavior of that method convert is 'ad-hoc' because of:

                          implicit convertor: Convertor[A]
      Частина ad-hoc: в коді в нас є можливість викликати convertor.convert тільки тоді коли convertor is supplied це є частина ad-hoc

      implicit convertor: Convertor[T] → ad-hoc
      [T]                              → поліморфізм, де для кожного специфічного типу ми маємо конкретну імплементацію

     */


    /* Using */
    import ConvertorSyntax._
    import ConvertorInstances._
    1.convert()
    true.convert()
  }



//  ✅✅✅✅✅✅  // Functor  ✅✅✅✅✅✅
  {
    /*
      Functor
        - provides the ability for its values to be "mapped over"
        - function that transforms inside a value while remembering it's shape
        - fundamental method is a "map"

      Example, modify every element of a collection without dropping or adding elements

      Laws:
        identity:               fa.map(x => x)    ==  fa
        composition:            fa.map(f).map(g)  ==  fa.map(x => f(x) andThen g(x))


      In mathematical concept:
        Functor       - defines transformations between categories.
                        in Scala - defines transformations between Scala types
        EndoFunctors  - defines transformations between same categories
                        F[A]  →  F[B]


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




//    ✅✅✅✅✅✅  // Monads   ✅✅✅✅✅✅

    /*
    Monads:
      - один із найважливіших building blocks of functional programming
      - обгорнути значення в контекст
      - і послідовно застосовувати до нього обчислення in a chain way
      - in the end have the same type
      - to be able write the for-comprehension we do need to have "map" and "flatMap"
      - for-comprehensions transform by compiler into the "map" and "flatMap"


      Extract → Transform → Wrap
      Monad has two fundamental operation
          Wrap a value: class `CustomMonad` wrap `value` In functional world it's named as a `pure` or `unit`
          Transform a value by the given function, in our case it's a `flatMap`: T => CustomMonad[S]

      "f": Monad(x)
      Monad(x).flatMap(f)               == f(x)                                     left identity
      Monad(x).flatMap(x => Monad(x))   == Monad(x)                                 right identity

      Monad(x).flatMap(f).flatMap(g)    == Monad(x).flatMap(x => f(x).flatMap(g))   composition, де f - функція котра повертає Monad(x)

            List(x).flatMap(f)        ==  f(x)
            xs.flatMap(x => List(x))  ==  xs
            xs.flatMap(f).flatMap(g)  ==  xs.flatMap(x => f(x).flatMap(g))
    */
  }



//  ✅✅✅✅✅✅   // Semigroup     ✅✅✅✅✅✅
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

    import SemigroupSyntax._
    import SemigroupInstances._

    1 |+| 2
    "foo" |+| "bar"
  }



//  ✅✅✅✅✅✅   // Monoid     ✅✅✅✅✅✅

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



//  ✅✅✅✅✅✅   // Loan - pattern     ✅✅✅✅✅✅
  {
    case class Session(url: String, isAlive: Boolean)

    def builder(handle: Session => Unit): Unit =
      handle(Session("www.trump.ua", true))

    builder { session =>
      println(session.url)
      println(session.isAlive)
    }
  }


//  ✅✅✅✅✅✅   // Singleton     ✅✅✅✅✅✅
  /*    In scala it's represented in one line just as object    */
  object Singleton



//  ✅✅✅✅✅✅   // Теория Cats     ✅✅✅✅✅✅

  /*
      Cats       -  is a library which provides abstractions for functional programming in the Scala programming language
      Cats goals -  support functional programming in Scala applications
      GENERAL FLOW FOR CATS:
          import cats.Eq
          import cats.instances.int._
          import cats.syntax.eq._
          import cats.implicits._

      Тео́рия катего́рий — раздел математики, изучающий свойства отношений между математическими объектами,
                         не зависящие от внутренней структуры объектов
            - математика                            Scala:
              - об’єкти:  множини                     типи (A, B)
              - морфізми: функції між множинами       функції (A => B)

                                                      val f: Int => String = _.toString
                                                      val g: String => Int = _.length
                                                      val composed = f.andThen(g) // композиція морфізмів

            - категория множеств
            - категория групп
            - категория модулей
            - категория векторных пространств

      морфизми                - стрелоки
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
                     функція, яку можна повністю “перевернути назад” без втрат
      - авто морфизм - ???
  */

  // Writer Monad
  // Reader Monad



















  // test
  {
    /* used for test behavior
    class SomeClass extends AnyFunSpec {
      describe("multiplication") {
        describe("...") {
          it("should give back 0") {
            ???
          }
        }
      }
    }


    class SomeOtherClass extends AnyWordSpec {
      "A calculator" should {
        "give bqck 0" in {
          ???
        }
      }
    }


    organize your test in a custom way as you wish
    class SomethingElse extends AnyFreeSpec {
      "any free way to write" - {
        ???
      }
    }

    class SomethingNew extends AnyPropSpec {
      property("...") {
        ???
      }
    }
    */
  }



  // how to make sure the type are the same
  /* (implicit evicted: A =:= B) - means types A and B must be the same otherwise it will not compile */
  def wrapper[A, B](la: List[A], lb: List[B])(implicit evicted: A =:= B): List[(A, B)] = ???


  // sbt
    /*
          assembly -  build a sbt .jar
          resolver -  is a think which allow us to set repository where sbt is going to search artifacts and libs
                      - can be internal - search in local machine: Resolver.mavenLocal()
                                          .m2/ folder where maven are going to look up
                      - can be external - search in a specific web:
                                Resolver.url("my-test-repo", "https://rockthejvm.com/repository")
          task     - we can create custom one as a separate scala file which we can invoke акщь sbt file than we can
                     hit or trigger that task via sbt console
                     -  can depend on each other
          object StringTask {
            def generateUUID(): String = { UUID.randomUUID() }
          }

          sbt file:
            lazy val uuidTask = taskKey[String]("Name of the custom task")
            uuidTask := { StringTask.generateUUID() }
          in sbt console run command:
            uuidTask


          custom setting - ability to save custom value in a "global variable". Something like javaScrips has with
                           variable named "property"

          sbt file:
            lazy val uuidSetting = settingKey[String]("custom name ...")
            uuidSetting:= {
                StringTask.generateUUID()
            }

          sbt console:
            uuidSetting

          task and setting - example. We do have generateUUID() method described in task and in setting.
                             Differences:
                                  task      ->  new UUID each time(dynamic)
                                  setting   ->  the same (static)

          command aliases - example:
                            sbt file:
                                addCommand("some_custom_name", "compile; test; assembly")
                            sbt console:
                                some_custom_name

          crossScalaVersions - allow us to compile the project for different versions. example
                            sbt file:
                              val scala212 = "scala 2.12"
                              val scala213 = "scala 2.13"

                              lazy val core = (project in file("core")).setting (
                                assembly / mainClass := Some("com.rockthejvm.CoreApp")
                                crossScalaVersions := List(scala212, scala213))

          name              - in sbt file -> name of the project
          build.properties  - it's a file where we can set sbt-version
          runMain           - command which is used to run main method from sbt console
          ~compile          - command from sbt console which allow us to recompile file automatically in case file was
                              changed
          libraryDependencies - list with additional libs
          test: testOnly     - command from sbt console to run test

          multiply projects - example
                            lazy val core   = (project in file("core"))
                            lazy val server = (project in file("server")).dependsOn(core)
                            lazy val root   = (project in file(.)).aggregate(core, server)

          plugin.sbt                      - file where plugins are added. As an example we can add docker plugin
          assembly                        - plugin and command which we run via sbt console which allow us to build jar file
          java -jar core-assembly-1.0.jar - run jar file
    */


}
