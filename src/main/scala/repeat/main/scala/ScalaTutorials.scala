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
  val _: String = null /* as in a Java world */
  val d: Null = null /* Null has no methods, no fields, can not be extended or instantiated  and only possible value is 'null'.
  It's extends all references types
  AnyRef -> all reference types -> Null
  */
  val _: String = d


  /* Unit is like a void in a java world*/
  /* Nothing. Examples: throw new NullpointerException,  ??? no value at all*/

}




