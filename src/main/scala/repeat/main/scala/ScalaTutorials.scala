package repeat.main.scala
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global



object ScalaTutorials {
  def main(args: Array[String]): Unit = {
    println("Is everything ok ???????")
  }


  // Variants | Covariant | Contravariant
  /*
  *   Dog is subtype of Animal
  *   Is List[Dog] is subtype of List[Animal] = this is variant question

  *   In case answer is YES         => it means Covariant | In Scala it marked as List[+A] */
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
  * Summary
  *   - In case your generic type produce(creates) or contain an elements   =>  COVARIANT
  *   - In case your generic type "acts on" or consume an elements          =>  CONTRAVARIANTx
  * */






  // SELF TYPE
  /*
  * Which mean everyone who extends Hospital MUST extends Builder
  *
  * */
  trait Builder {
    def build(asd: String): Boolean = true
  }
  trait Hospital { selfType: Builder =>
    def makeNewHospital(value: Int): Boolean = selfType.build(value.toString)
  }

  class makeNewStructure extends Hospital with Builder {
    def someAwesomeMethod(): Unit = makeNewHospital(3)
  }

  
  // Nothing
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


  // call by name
  def byValueFunction(x: Int): Int = x + 1
  byValueFunction(3 + 2) /* 3 + 2 evaluated before method "byValueFunction" are going to be called*/

  def byNameFunction(x: => Int): Int = x + 1
  byNameFunction(3 + 2)  /* 3 + 2 are going to be evaluated when it's going to be used inside that function "byNameFunction".
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
  *  That pattern is named as "call by need". It powerful in infinity collections
  * */

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
  * */



  //  Blocking | Async | Non-blocking

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
  * as an example akka actor which is basically data structure
  * */


  // right associative method
  class MyClass_2 {
    def :: (a: Int): Unit = println("")
  }
  val myClass = new MyClass_2
  123 :: myClass /* because method in class MyClass_2 ended with a ":" */



  // Type class - is data structure which support ad hoc polymorphism

  trait Convertor[T] {
    def convert(list : List[T]): T
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

  summup(List(1,2,3))
  summup("asd" :: "123" :: Nil)



  // null, Null, Nothing, None
  /* null */
  val g: String = null /* as in a Java world */
  val d: Null = null /* Null has no methods, no fields, can not be extended or instantiated  and only possible value is 'null'.
  It's extends all references types
  AnyRef -> all reference types -> Null
  */
  val y: String = d


  /* Unit is like a void in a java world*/
  /* Nothing. Examples: throw new NullpointerException,  ??? no value at all*/



  // Abstract class vs trait
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
  * */


  /*
  * - can inherit from a SINGLE abstract class
  * - can inherit from MULTIPLE traits
  * - abstract class can take constructor arguments
  * - trait can't take constructor arguments
  * - represent things as a classes
  * - represent behavior as traits
  * */


  // Eta-Expansion & Partially applied function
  /*
  * - function can be assigned to a value which can be passed as an argument
  * - function is an instance of a function traits family
  * - method depends on a class or object where it defined where function is a plain object
  * - eta-expansion is a transform method into the function
  *       val something = someMethod _
  *  example:
  *   List(1,2,3,4).map(incrementMethod) <- compiler automatically transform "incrementMethod" into the function
  * 
  * */



  // Types system
  val anInt: Int = 123    // level - 0 type
  class MyAwesomeList[T]  // level - 1 type (type constructor)
  class Function[F[_]]    // level - 2 type
  class Meta[F[_[_]]]     // level - 3 type




  // call-by need pattern
  /*
  * This pattern is about lazy evaluations where class parameters are declared as call-by name and values marked as a
  * lazy val ... Example can be find bellow (linked list)
  * */

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

  }


}




