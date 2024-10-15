package repeat.main.scala
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global



/*
*   TODO:
*     Functional programing'
*
*       https://www.youtube.com/watch?v=ozcY_K-ij20&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=52
*       https://www.youtube.com/watch?v=m3Qh-MmWpbM&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=51
*       https://www.youtube.com/watch?v=Y5rPHZaUakg&list=PLmtsMNDRU0Bzj7INIrLugi3a_WClwQuiS&index=45
*
*
* */


object ScalaTutorials {
  def main(args: Array[String]): Unit = {
    println("Is everything ok ???????")
  }


  // referential transparency
  /*
   -  property to replace expression without changing meaning of the program
   -  in other words we can replace expression with a value which expression evaluates
   example:
  * */
  def add(a: Int, b: Int): Int = a + b
  val five = add(2, 3)
  val b = five + five
  val ten_v1 = add(2, 3) + add(2, 3)
  val ten_v2 = 5 + add(2, 3)

  /* referencial transparency is not */
  def showTime(): Long = System.currentTimeMillis()


  // unapply
  {
    case class Pet(age: Int, name: String)
    object Pet {
      def unapply(pet: Pet): Option[(Int, String)] =
        pet.age > 10 match {
          case true => Some(10, "...")
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







  // general notes
  {
    /*
      scala vision              - we think about program as one expression which return a value which can me
                                  complex like server application. Think in terms of expression instead of instructinos.
                                  We an instructino problem which means evaluate the expresion while maintaining type safty
                                  and return the right value with the right type
      associativity             - a x (b x c) = (a x b) x c
      infix notation            - fundamental feature in language. Ability to use method without any dots or parentheses
      auxiliary constructors    - Overloaded constructor this(). All auxiliary constructors must first call the primary
                                  constructor or another auxiliary constructor. Guarantees that the object is properly
                                  initialized before the auxiliary constructor comes into play
      lifting                   - when we pass some function as if it were a function, Scala automatically converts it
                                  to a function object.

      scala prefixes            - all the parameters with val
      equals/hashCode           - implemented for you based on the given parameters
      companion object          - created with the appropriate apply method, which takes the same arguments as declared in the class
      unapply                   - allow the class name to be used as an extractor for pattern matching

      sealed trait              - all the classes need to be in the same source file


      first-class citizens      - we can use functions as values or like normal variables
      default scala imports     - implicitly: java.lang._, scala._, scala.Predef._ */
  }





  // value classes
  {
    /*
      case class BarCode(value: String) extends AnyVal {
        def countryCode: Char = value.charAt(0)
    }*/

    /*
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



  // implicit lookup
  {
    /*    Look in current scope
            - Implicit defined in current scope
            - Imports explicit, wildcard
        Look at associated types in
            - Companion object of a type
            - Companion object OtherClass
            - Companion object B
            - Any superclass of B */
  }




  // substitution model
  /*
        sumOfSquares(3, 2 + 2)
        sumOfSquares(3, 4)
        square(3) + square(4)
        3 * 3 + square(4)
        9 + square(4)
        9 + 4 * 4
        9 + 16
        25
                           */




  // call by name
  {
    def byValueFunction(x: Int): Int = x + 1
    byValueFunction(3 + 2) /* 3 + 2 evaluated before method "byValueFunction" are going to be called*/
  def byNameFunction(x: => Int): Int = x + 1
    byNameFunction(3 + 2) /* 3 + 2 are going to be evaluated when it's going to be used inside that function "byNameFunction".
     in other words => call by need */

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


    /* example with infinity list where tail or in other words all structure evaluated when it's needed.
       That pattern is named as "call by need". It powerful in infinity collections
       */

    abstract class MyList1[+T] {
      def head: T

      def tail: MyList1[T]
    }

    case object EmptyList extends MyList1[Nothing] {
      override def head: Nothing = throw new NullPointerException

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

       In case answer is YES         => it means Covariant | In Scala it marked as List[+A] */
    class Animal
    class Dog extends Animal
    val _: List[Animal] = List.empty[Dog]

    /*   In case answer is NO         => it means Invariant where no relationships between List[Animal] and List[Dog]. In Scala it marked as List[A] */
    class MyList[A]
    val _: MyList[Animal] = new MyList[Animal]

    /*    In case answer is HELL NO   => it means Contravariant. Where in Scala it marked as List[-A] */
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
        - In case your generic type produce(creates) or contain an elements   =>  COVARIANT
        - In case your generic type "acts on" or consume an elements          =>  CONTRAVARIANTx
   */

  }




  // self type
  {
    /*  Which mean everyone who extends Hospital MUST extends Builder  */
    trait Builder {
      def build(asd: String): Boolean = true
    }
    trait Hospital {
      selfType: Builder =>
      def makeNewHospital(value: Int): Boolean = selfType.build(value.toString)
    }

    class makeNewStructure extends Hospital with Builder {
      def someAwesomeMethod(): Unit = makeNewHospital(3)
    }
  }




  // null, Null, Nothing, None
  {
    /* null */
    val g: String = null
    /* as in a Java world */
    val d: Null = null
    /* Null has no methods, no fields, can not be extended or instantiated  and only possible value is 'null'.
        It's extends all references types
        AnyRef -> all reference types -> Null
     */
    val y: String = d

    /* Unit is like a void in a java world*/
    /* Nothing. Examples: throw new NullpointerException,  ??? no value at all*/
  }




  // nothing
  {
    class MyClass
    val a: String = throw new NullPointerException
    val b: Int = throw new NullPointerException
    val c: MyClass = throw new NullPointerException

    /* can we use Nothing ? */
    def someFunction(a: Nothing): Int = ???
    def someFunction2(a: Nothing): Nothing = throw new NullPointerException

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
      def discussWith(abother: Person): String
    }

    trait PersonTrait {
      def canFly: Boolean = true
      val canDrive: Boolean
      def discussWith(abother: Person): String
    }

    /*
    - they can't be instantiated on their own
    - may have abstract fields/methods
    - may have non-abstract fields/methods


    - can inherit from a SINGLE abstract class
    - can inherit from MULTIPLE traits
    - abstract class can take constructor arguments
    - trait can't take constructor arguments
    - represent things as a classes
    - represent behavior as traits
                                                      */
  }




  // Eta-Expansion & Partially applied function
  {
    /*
     - function can be assigned to a value which can be passed as an argument
     - function is an instance of a function traits family
     - method depends on a class or object where it defined where function is a plain object
     - eta-expansion is a transform method into the function
           val something = someMethod _
      example:
       List(1,2,3,4).map(incrementMethod) <- compiler automatically transform "incrementMethod" into the function

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


    /* async non-blocking when current thread or other one are not blocked
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




  // Big O notation
  {
    /* https://www.youtube.com/watch?v=ZRdOb4yR0kk

        Big O - показивает верхнюю межу складності виконання алгоритма в залежності від вхвідних параметрів.
        Ми не беремо до уваги константи та "наважну" складність

      - послідовність дій                                                => додавання
      - вложеність дій                                                   => множення
      - для алгоритма де на конжній ітерації береться половина елементів => log N


        О(N^2 + N^2)            = O(N^2)
        О(N^2 + N)              = O(N^2)
        О(N + logN)             = O(N)
        O(5 * 2^N + 10*N^100)   = O(2^N)     2^N - растет гараздо бистрее чем N^100


       O (big 0): In academia, big O describes an upper bound on the time.
           An algorithm that prints all the values in an array could be described as O(N),
           but it could also be described as O(N2), O(N3) ... therefore they are upper
           bounds on the runtime.
           X <= 1, 000 or X <= 1,000,000. It's technically true

      `0` (big omega): In academia, `0` is the equivalent concept but for lower bound.
               Printing the values in an array is O(N) as well as O(log N) and 0(1).
               After all, you know that it won't be faster than those runtimes.

      `E` (big theta): In academia, `E` means both O and `0`. That is, an algorithm is E(N) if it is both O(N) and
                 `0`( N). 0 gives a tight bound on runtime.


       Рекурсивна фунція яка рахує сумму чисел
       У випадку коли N = 3 функція викличе себе 3 рази
       у випадку коли N = 4 функція викличе себе 4 рази

       Швидкодія:   О (N)
     */
    def sum(n: Int): Int = n match {
      case 1 => 1
      case b => b + sum(b - 1)
    }


    /*  Функція котра пробігається по всьому масиву і додвє два числа.
        Швидкодія: O(N)
    */
    def sumSuqences(n: Int): Int = (0 to n).map(k => innerSumFuntion(k, k + 1)).sum


    /* швидкодія: О(1) */
    def innerSumFuntion(a: Int, b: Int): Int = a + b


    /* Швидкодія: O(A + B) */
    def function_1(array_1: Array[Int], array_2: Array[Int]): Unit = {
      array_1.foreach(println)
      array_2.foreach(println)
    }


    /* Швидкодія: O(N^2) томущо є вложеність */
    def function_2(array_1: Array[Int]): Unit = {
      array_1.foreach(a =>
        array_1.foreach(b =>
          println(a + b)))
    }


    /* Швидкодія: O(A * B) томущо є вложеність */
    def function_3(array_A: Array[Int], array_B: Array[Int]): Unit = {
      array_A.foreach(a =>
        array_B.foreach(b =>
          println(a + b)))
    }


    /* Швидкодія: O(N) */
    def function_4(array: Array[Int]): Unit = (1 to array.length / 2).foreach(println)


    /*  O(log N) -> для алгоритма, де на кожній ітерації береться половина елементів складність буде включати log N     */

  }




  // Algorithm and data structure
  {

    /*
    * Find all coins which in sum will be equal some number.
    * For example:
    *   coins: 1,5,10
    *   number: 26
    *   answer: 10, 10, 5, 1
    *
    *
    * Solution above:
    * */
    val cents = 1 :: 5 :: 10 :: Nil
    val number = 26

    def function(number: Int, cents: Set[Int], result: List[Int] = Nil): List[Int] =
      number match {
        case n if n <= 0 => result
        case _ if cents.nonEmpty =>
          number - cents.max match {
            case 0 => result :+ cents.max
            case n if n < 0 => function(number, cents.-(cents.max), result)
            case n if n > 0 => function(number - cents.max, cents, result :+ cents.max)
          }
        case _ => result
      }

    function(number, cents.toSet)


    /*
    * Almost the same task but with a small changes.
    * Changes: find more optima solution
    *
    * For example:
    *   coins: 1,5,10,20,25
    *   number: 41
    *   answer: 20,20,1
    *
    *   Solution: list with a lowest length
    *
    * */
    val cents_1 = 1 :: 5 :: 10 :: 20 :: 25 :: Nil
    val number_1 = 41

    def function_1(number: Int, cents: Set[Int], result: List[Int] = Nil): List[Int] =
      number match {
        case n if n <= 0 => result
        case _ if cents.nonEmpty =>
          number - cents.max match {
            case 0 => result :+ cents.max
            case n if n < 0 => function(number, cents.-(cents.max), result)
            case n if n > 0 => function(number - cents.max, cents, result :+ cents.max)
          }
        case _ => result
      }

    def function_2(number: Int, cents: List[Int]): List[List[Int]] =
      cents.sorted.inits.foldLeft(List.empty[List[Int]]) {(result, cents) =>
        result :+ function_1(number, cents.toSet)
      }

    function_2(number_1, cents_1).mkString("\n")
    /*
    * result is:
    *
    * List(25, 10, 5, 1)
    * List(20, 20, 1)                       <---------- this is an optimal solution
    * List(10, 10, 10, 10, 1)
    * List(5, 5, 5, 5, 5, 5, 5, 5, 1)
    * List(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    * */


    /*
    * Bubble sort. implemented in mutable way
    *
    * Solution:
    * */
    import scala.collection.mutable.{ArraySeq => MutableArray}

    val list = 1 :: 3 :: 2 :: 4 :: 6 :: 5 :: 7 :: Nil

    def function_3(elements: List[Int]): MutableArray[Int] = {
      val mutableArray = MutableArray.from(elements)

      (1 until elements.length).foreach {_ =>
        (0 until elements.length - 1).foreach {stepIndex =>
          val nextIndex = stepIndex + 1
          val stepElement = mutableArray(stepIndex)
          val nextElement = mutableArray(nextIndex)

          if (stepElement > nextElement) {
            mutableArray(nextIndex) = stepElement
            mutableArray(stepIndex) = nextElement
          }
        }
      }
      mutableArray
    }

    function_3(list.reverse)


    /*
    * Select sort.
    * 1. pick up the first element
    * 2. find the lowest element in an array
    * 3. compare element in step 1 with an element in step 2
    * 4. swap elements in case first element is not lowest
    * 5. pick up second element
    * 6. then step 2 and so on...until end of an array
    *
    * Solution:
    * */

    val list_2 = 1 :: 3 :: 2 :: 4 :: 6 :: 5 :: 7 :: 0 :: Nil

    def function_4(elements: List[Int]): MutableArray[Int] = {

      val mutableArray = MutableArray.from(elements)

      (0 until mutableArray.length - 1).foreach {index =>
        val indexWithLowestElem = (index + 1 until mutableArray.length).foldLeft(index) {
          (indexWithLowestElem, stepIndex) =>
            mutableArray(indexWithLowestElem) >= mutableArray(stepIndex) match {
              case true => stepIndex
              case false => indexWithLowestElem
            }
        }

        /* swap if lowest element was found otherwise ignore */
        if (indexWithLowestElem != index) {
          val stepElement = mutableArray(index)
          mutableArray(index) = mutableArray(indexWithLowestElem)
          mutableArray(indexWithLowestElem) = stepElement
        }
      }

      mutableArray
    }

    function_4(list_2.sorted.reverse)


    /*
    * Insert sort: O(n^2)
    * 1. Pick up an element
    * 2. Pick up previous element
    * 3. Compare 1 and 2
    * 4. Swap in case 2 is higher than 1
    *
    * Solution:
    * */

    val list_3 = 7 :: 6 :: 5 :: 4 :: 10 :: 3 :: 13 :: 2 :: 1 :: 0 :: Nil

    def function_5(element: List[Int]): MutableArray[Int] = {
      val mutableArray = MutableArray.from(element)
      (0 to mutableArray.length - 1).foreach {i =>
        var j = i
        while (j > 0 && mutableArray(j - 1) > mutableArray(j)) {
          val previous = mutableArray(j - 1)
          mutableArray(j - 1) = mutableArray(j)
          mutableArray(j) = previous
          j = j - 1
        }
      }

      mutableArray
    }


    /*
    * Merge sort: O(logN)
    *
    *
    *  Solution:
    * */
    val list_4 = 7 :: 6 :: 5 :: 4 :: 10 :: 3 :: 13 :: 2 :: 1 :: 0 :: Nil

    def merge(listOne: List[Int], listTwo: List[Int]): List[Int] = (listOne, listTwo) match {
      case (Nil, list) => list
      case (list, Nil) => list
      case (x :: xs, y :: ys) =>
        if (x < y) x :: merge(xs, listTwo)
        else y :: merge(listOne, ys)
    }

    def mergeSort(list: List[Int]): List[Int] = list match {
      case Nil => list
      case xs :: Nil => List(xs)
      case _ =>
        val (left, right) = list splitAt list.length / 2
        merge(mergeSort(left), mergeSort(right))
    }

    mergeSort(list_4)



    /* insert sort */

    def insertSort(list: List[Int]): List[Int] = {
      def insert(number: Int, sortedList: List[Int]): List[Int] = {
        if (sortedList.isEmpty || number < sortedList.head) number :: sortedList
        else sortedList.head :: insert(number, sortedList.tail)
      }

      if (list.isEmpty || list.tail.isEmpty) list
      else insert(list.head, insertSort(list.tail))
    }

    assert(insertSort(Nil) == Nil)
    assert(insertSort(List(1)) == List(1))
    assert(insertSort(List(3,2,1)) == List(1,2,3))
    assert(insertSort(List(3,2,1,4,5,9,0)) == List(0,1,2,3,4,5,9))
  }




  // algebra data type | ADT
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
        1. Illigal states ar not representable.
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


    class SomeOtherClas extends AnyWordSpec {
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



  //                          **********      Patterns        **********

  //  Type classes
  {
    /*  - is a data structure which support ad hoc polymorphism
      - we can ADD NEW METHOD or NEW DATA without changing existing code
      There are three important point about type classes:
        - Type class itself
        - Instances for particular type
        - Interface method that we exposed to user
  */

    trait Convertor[T] {
      def convert(list: List[T]): T
    }

    object Convertor {
      implicit object IntSummup extends Convertor[Int] {
        override def convert(list: List[Int]): Int = list.sum
      }

      implicit object StrSummup extends Convertor[String] {
        override def convert(list: List[String]): String = list.mkString(", ")
      }
    }


    /*
    The behavior of that method is 'ad-hoc' because of: implicit convertor: Convertor[T] where in the code we have capability to call
    convert method on convertor only when convertor is supplied. That is the part of ad-hoc.
    Polymorphism part is the [T] - where for any specific type we must have it's own implementation

    implicit convertor: Convertor[T] <- ad-hoc
    [T]                              <- polymorphism

  */
    def summup[T](list: List[T])(implicit convertor: Convertor[T]): T = /* ad-hoc polymorphism */
      convertor.convert(list)

    summup(List(1, 2, 3))
    summup("asd" :: "123" :: Nil)
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


    object DLLEnpty extends DLLList[Nothing] {
      override def value: Nothing = throw new NoSuchElementException("There are no element")
      override def next: DLLList[Nothing] = throw new NoSuchElementException("There are no element")
      override def prev: DLLList[Nothing] = throw new NoSuchElementException("There are no element")
      override def append[S >: Nothing](element: S) = new DLLCons(element, DLLEnpty, DLLEnpty)
      override def prepend[S >: Nothing](element: S) = new DLLCons(element, DLLEnpty, DLLEnpty)
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

      val list = DLLEnpty.prepend(1).append(2).prepend(3).append(4)
      assert(list.value == 1)
      assert(list.next.value == 2)
      assert(list.next.prev == list)
      assert(list.prev.value == 3)
      assert(list.prev.next == list)
      assert(list.next.next.value == 4)
      assert(list.next.next.prev.prev == list)
    }
  }




  // Functor
  {
  /*
      A Functor for a type provides the ability for its values to be "mapped over",
    i.e. apply a function that transforms inside a value while remembering its shape.
    For example, to modify every element of a collection without dropping or adding elements.

   Laws:
     identity:               fa.map(x => x)    ==  fa
     composition:            fa.map(f).map(g)  ==  fa.map(x => f(x) andThen g(x))


   */

    trait Functor[C[_]] {
      def map[A, B](container: C[A])(function: A => B): C[B]
    }


    object Functor {

      implicit object ListFunctor extends Functor[List] {
        override def map[A, B](container: List[A])(function: A => B): List[B] = container.map(function)
      }

      implicit object OptionFunctor extends Functor[Option] {
        override def map[A, B](container: Option[A])(function: A => B): Option[B] = container.map(function)
      }

      implicit object TreeFunctor extends Functor[Tree] {
        override def map[A, B](container: Tree[A])(function: A => B): Tree[B] = container match {
          case Leaf(value) => Leaf(function(value))
          case Branch(value, left, right) => Branch(function(value), map(left)(function), map(right)(function))
        }
      }
    }


    trait Tree[+T]
    object Tree {
      def leaf[T](value: T): Tree[T] = Leaf(value)
      def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
    }

    case class Leaf[+T](value: T) extends Tree[T]
    case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

    def devx10[C[_]](container: C[Int])(implicit functor: Functor[C]) = {
      functor.map(container)(_ * 10)
    }

  }




  // Loan - pattern
  {
    case class Session(url: String, isAlive: Boolean)

    def builder(handle: Session => Unit): Unit = {
      handle(Session("www.trump.ua", true))
    }

    builder {session =>
      println(session.url)
      println(session.isAlive)
    }
  }



  // singleton
  /*    In scala it's represented in one line just as object    */
  object Singleton



  // companion object
  /*  class + object = companion  */
  class Kid {
    val _ = Kid.privateField
  }

  object Kid { private val privateField = 123 }




}






