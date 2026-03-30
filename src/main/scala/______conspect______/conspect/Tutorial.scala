package ______conspect______.conspect

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/*
*   TODO:
*     Functional programing
*
*       Generic - https://www.youtube.com/watch?v=ozcY_K-ij20&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=52
*       Meta    - https://www.youtube.com/watch?v=m3Qh-MmWpbM&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=51
*                 https://www.youtube.com/watch?v=Y5rPHZaUakg&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=45
* */


object Tutorial {
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
    - referential transparency  → замінити функцію на val до котрої та функція заасайнина
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

    /*   In case answer is NO         → it means Invariant where no relationships between List[Animal] and List[Dog]. In Scala it marked as List[A] */
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
    ADT  =  algebra data type
      SUM       →  object
      Product   →  case class
      Hybrid    →  case class contains object Product(SUM)
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
