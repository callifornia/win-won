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


  // OOP vs Functional Programing
  /*
    https://doc.akka.io/libraries/akka-core/2.6/typed/guide/actors-motivation.html#the-challenge-of-encapsulation

   In Object Oriented languages we rarely think about threads or linear execution paths in general
   We often envision a system as a network of object instances that react to method calls, modify their internal state,
   then communicate with each other via method calls driving the whole application state forward:
      -  CPUs are writing to cache lines instead of writing to memory directly
      -  shipping cache lines across cores is a very costly operation
      -  most of these caches are local to the CPU core
      -  writes by one core are not visible by another core
      -  cache line needs to be shipped to the other core’s cache
      -  on the JVM, to be shared across threads using volatile or Atomic wrappers
      -  an important difference between passing messages and calling methods is that messages have no return value
      -  actor system can process as many messages simultaneously as the hardware will support


    - functional programming    → work with a function as we work with a value
    - function value            → can be passed around or return as a result

    - pattern matching          → decomposition of an element
    - partial function          → in other words it's pattern matching

    - higher kinder types       → when one type accept another type
    - higher order function     → when one function accept another function
    - inheritance model         → extends 1 class but lots of traits
    - instruction vs expression:

              Expression                  →     produces a value
              Instruction (statement)     →     performs an action

              Expression example:
                  val x = 1 + 2
                  val y = if (x > 2) "big" else "small"
                  both return value ...
              Instruction example:
                  println("Hello")
                  var x = 10
                  x = x + 1

    - method notation example:  1 + 2
    - scala vision              → we think about program as one expression which evaluates and return a value which can be
                                  complex like server application. Think in terms of expression instead of instructions(imperative approach).
                                  We have an instruction problem which means evaluate the expression while maintaining type safety
                                  and return the right value with the right type
                                  Think of functions as values

    - associativity             → a x (b x c) = (a x b) x c
    - infix notation            → ability to use method without any dots or parentheses, fundamental feature in language
    - auxiliary constructors    → overloaded constructor this()
                                  must first call the primary constructor or another auxiliary constructor
    - lifting                   → when we pass some method as if it were a function, Scala automatically converts it
                                  to a function object

    - scala prefixes            → all the parameters with val
    - equals/hashCode           → implemented for you based on the given parameters
    - companion object          → created with the appropriate apply method, which takes the same arguments as declared in the case class
    - unapply                   → allow the class name to be used as an extractor for pattern matching
    - sealed trait              → all the classes need to be in the same source file
    - first-class citizens      → we can use functions as values or like normal variables
    - default scala imports     → implicitly: java.lang._, scala._, scala.Predef._
    - linearization             → This is an important concept in Scala that explains how Scala resolves methods when multiple traits are mixed
                                  together. When a method is called, Scala looks for the implementation from left to right in this order
                                  This solves the diamond problem found in multiple inheritance
    - eta-expansion             → перетворення метода у функцію
                                  val function = someMethod _
                                  List(1,2,3,4).map(someMethod)

    - function                  → is an instance of a function traits family: example: new Function[Int, Int]
    - method                    → depends on a class or object where it defined where function is a plain object
    - referential transparency  → це властивість виразу, коли його можна замінити на його значення без зміни поведінки програми.
                    def add(a: Int, b: Int): Int = a + b
                    val five   = add(2, 3)
                    val _      = five + five
                    val ten_v1 = add(2, 3) + add(2, 3)
                    val ten_v2 = 5 + add(2, 3)

                    /* referential transparency is not */
                    def showTime(): Long = System.currentTimeMillis()

     - implicit lookup:
          Look in current scope
            - implicit defined in current scope
            - imports explicit, wildcard
          Look at associated types in
            - companion object of a type
            - companion object OtherClass
            - companion object B
            - any superclass of B

     - substitution model:
          sumOfSquares(3, 2 + 2)
          sumOfSquares(3, 4)
          square(3) + square(4)
          3 * 3 + square(4)
          9 + square(4)
          9 + 4 * 4
          9 + 16
          25

   */


  // unapply
  {
    case class Pet(age: Int, name: String)
    object Pet {
      /*     case Pet               (age, name)             */
      def unapply(pet: Pet): Option[(Int, String)] =
        pet.age > 10 match {
          case true  => Some(10, "...")
          case false => Some(12, "...")
        }
      /*     case Pet               (name)                  */
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


  // value classes
  {
    /*

                                case class BarCode(value: String) extends AnyVal {
                                    def countryCode: Char = value.charAt(0)


        Restriction:
            - no vals only def
            - only one constructors
            - can not be extended
            - can only extend universal trait (traits with just def and without initialization)
            - no runtime overhead, in heap we are going to have String instead of BarCode
            - instance of the BarCode is going to be created only if it in use in generic type:
                          def show[T](agr: T): String = agr.toString
                          show(BarCode("..."))

                          Array[BarCode](BarCode("..."))

                          BarCode("...") match {
                            case BarCode(_) => println("...")
                          }

*/
  }

  /*


                           */



  // call by name
  {
    def byValueFunction(x: Int): Int = x + 1
    byValueFunction(3 + 2) /*     3 + 2 evaluated before method "byValueFunction" are going to be called        */


    def byNameFunction(x: => Int): Int = x + 1
    byNameFunction(3 + 2) /*      3 + 2 are going to be evaluated when it's going to be used inside that function "byNameFunction".
                                        in other words → call by need */

    /*    example with reevaluation   */
    def byValue(x: Long): Unit = {
      println(x)
      println(x)
    }

    def byName(x: => Long): Unit = {
      println(x)
      println(x)
    }

    byValue(System.nanoTime())
    byName(System.nanoTime())


    /*
       example with infinity list where tail or in other words all structure evaluated when it's needed
       that pattern is named as "call by need". It powerful in infinity collections
    */

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


    /*
        - call by name also used in a Future which allows to execute some functionality in some time in some thread
        - Try as an example also implemented in such way: Try(throw new NullPointerExceptions)
    */
  }




  // variants | covariant | contravariant
  {
    /*
       Dog is subtype of Animal
       Is List[Dog] is subtype of List[Animal] = this is variant question

       In case answer is YES         → it means Covariant | In Scala it marked as List[+A] */
    class Animal
    class Dog extends Animal
    val _: List[Animal] = List.empty[Dog]

    /*   In case answer is NO         → it means Invariant where no relationships between List[Animal] and List[Dog].
                                        In Scala it marked as List[A] */
    class MyList[A]
    val _: MyList[Animal] = new MyList[Animal]

    /*    In case answer is HELL NO   → it means Contravariant. Where in Scala it marked as List[-A] */
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


  // null, Null, Nothing, None
  {
    /* null */
    val g: String = null  /* as in a Java world */
    val d: Null = null/* Null -> no methods
                              -> no fields,
                              -> can not be extended or instantiated  and only possible value is 'null'
                         It's extends all references types
                         AnyRef -> all reference types -> Null
     */
    val y: String = d

    /* Unit is like a void in a java world*/
    /* Nothing. Examples: throw new NullpointerException,  ??? no value at all */
  }


  // nothing
  {
    class MyClass
    val a: String   = throw new NullPointerException
    val b: Int      = throw new NullPointerException
    val c: MyClass  = throw new NullPointerException

    /* can we use Nothing ? */
    def someFunction(a: Nothing): Int      = ???
    def someFunction2(b: Nothing): Nothing = throw new NullPointerException

    /* use in covariant side */
    abstract class MyList2[+T]
    class MyListSpec[T] extends MyList2[T]
    object MyListSpecEmpty extends MyList2[Nothing]
  }



  // abstract class vs trait
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

    /*
        - they can't be instantiated on their own
        - may have abstract fields/methods
        - may have non-abstract fields/methods

        - can inherit from a SINGLE abstract class
        - can inherit from MULTIPLE traits
        - abstract class can take constructor arguments
        - trait can't take constructor arguments
        - note: represent things as a classes
        - note: represent behavior as traits
     */
  }




  //  Blocking | Async | Non-blocking
  {
    /*  Blocking */
    def blockingCode(a: Int): Int = {
      Thread.sleep(10000)
      23
    }

    blockingCode(23)
    val _: Int = 33 /* will wait 10 seconds before evaluated */


    /* async blocking */
    def asyncBlockingCode(a: Int): Future[Int] = Future {
      Thread.sleep(1000)
      12
    }

    asyncBlockingCode(123)
    val _: Int = 123 /* evaluate immediate without any delay */


    /*
        async non-blocking when current thread or other one are not blocked
        as an example akka actor which is basically data structure
      */


    /* right associative method */
    class MyClass_2 {
      def ::(a: Int): Unit = println("")
    }
    val myClass = new MyClass_2
    123 :: myClass /* because method in class MyClass_2 ended with a ":" */
  }



  // Types system
  {
    val anInt: Int = 123        /*  level - 0 type */
    class MyAwesomeList[T]      /*  level - 1 type (type constructor) */
    class Function[F[_]]        /*  level - 2 type */
    class Meta[F[_[_]]]         /*  level - 3 type */
  }


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


  /*
    ADT  ==>  algebra data type
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
    ################################ Patterns ################################################################

    SOLID:
      Single responsibility  - каждий клас должен иметь одну и только одну причину для изменения
      Open closed            - открит для розширения, закрит для изменения (только дописивать)
      Liskov substitution    - функции которие используют базовий тип должни без изменения использовать
                               подтип
      Interface segregation  - много интерфейсов лучше чем один большой
      Dependency inversion   - зависимость на абстракции нет завимостей на конкретное


    Поліморфізм — це можливість працювати з різними типами даних через один і той самий інтерфейс або ім’я методу
          --------------------------------------------------------------------------------------------------------
          def identity[A](x: A): A = x

          val a = identity(5)        // Int
          val b = identity("hello")  // String
          --------------------------------------------------------------------------------------------------------

          trait Animal {
            def sound(): String
          }

          class Dog extends Animal {
            def sound() = "Woof"
          }

          class Cat extends Animal {
            def sound() = "Meow"
          }

          def makeSound(animal: Animal): Unit = {
            println(animal.sound())
          }

          makeSound(new Dog)
          makeSound(new Cat)

          Пояснення: Одна функція makeSound працює з різними типами (Dog, Cat)
* */

  // self type
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

    import SemigroupSyntax._
    import SemigroupInstances._

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



  // Loan - pattern
  {
    case class Session(url: String, isAlive: Boolean)

    def builder(handle: Session => Unit): Unit =
      handle(Session("www.trump.ua", true))

    builder { session =>
      println(session.url)
      println(session.isAlive)
    }
  }



  // Singleton
  /*    In scala it's represented in one line just as object    */
  object Singleton











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
