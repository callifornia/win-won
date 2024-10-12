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



  //  Type class - is a data structure which support ad hoc polymorphism
  /*  We can ADD NEW METHOD or NEW DATA without changing existing code
      There are three important point about type classes:
        - Type class itself
        - Instances for particular type
        - Interface method that we exposed to user
  */

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
  val anInt: Int = 123    /*  level - 0 type */
  class MyAwesomeList[T]  /*  level - 1 type (type constructor) */
  class Function[F[_]]    /*  level - 2 type */
  class Meta[F[_[_]]]     /*  level - 3 type */





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

    val list = DLLEnpty.prepend(1).append(2).prepend(3).append(4)
    assert(list.value == 1)
    assert(list.next.value == 2)
    assert(list.next.prev == list)
    assert(list.prev.value == 3)
    assert(list.prev.next == list)
    assert(list.next.next.value == 4)
    assert(list.next.next.prev.prev == list)

  }



  // referentially transparent - завжди повертає один і той самий результат
  // infix notation - fundamental feature in language. Ability to use method without any dots or parentheses
  // auxiliary constructors - Overloaded constructor this(). All auxiliary constructors must first call the primary
  //                          constructor or another auxiliary constructor. Guarantees that the object is properly
  //                          initialized before the auxiliary constructor comes into play
  // lifting - when we pass some function as if it were a function, Scala automatically converts it to a function object.
  // Why is it preferable to leave the parentheses off methods that don’t change an object? Programmers who call getB
  // should not have to care whether getB is a field (val) or a method (def). The caller only cares that getB produces
  // the desired value, not how it happens (this is the Uniform Access Principle).



  //  - Scala prefixes all the parameters with val, and that will make them public value. But remamber that you still
  //    never access the value directly; you always access through accessors
  //  - Equals/hashCode are implemented for you based on the given parameters
  //  - toString method implemented. Method return class name and its parameters
  //  - copy method. This method allows you to easily create a modified copy of the class’s instance
  //  - a companion object is created with the appropriate apply method, which takes the same arguments as declared in the class
  //  - unapply, which allow the class name to be used as an extractor for pattern matching
  //  - when you declare an abstract case class, Scala won’t generate the apply method in the companion object
  //    That makes sense because you can’t create an instance of an abstract class
  //  - to extend a sealed trait, all the classes need to be in the same source file


  //  - Scala also lets you qualify the private modifier: private[this]. In this case, it means object private.
  //    And object private is only accessible to the object in which it’s defined
  //
  //  - protected modifier is applicable to class member definitions. It’s accessible to the defining class and its
  //    subclasses. It’s also accessible to a companion object of the defining class and companion objects of all the
  //    subclasses. Like the private modifier, you can also qualify the protected modifier with class, package, and this.
  //    By default, when you don’t specify any modifier, everything is public. Scala doesn’t provide any modifier to
  //    mark members as public.

  //  - first-class citizens:  We can use functions as values or like normal variables; we can replace a variable or
  //    value with a function

  //  - what packages scala imports implicitly: java.lang._, scala._, scala.Predef._
  //    Implicit scope

  //    Look in current scope
  //      1. Implicit defined in current scope
  //      2. Imports explicit, wildcard
  //    Look at associated types in
  //      1. Companion object of a type
  //      2. Companion object OtherClass
  //      3. Companion object B
  //      4. Any superclass of B




  // Functor
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
        case Leaf(value)                => Leaf(function(value))
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

  


  // Loan - pattern
  /* ... continue here ...
  * https://docs.google.com/document/d/1wWwg0ATcr5HvXIk1wPW8a1JiTvi9MvwFAF5jxHGRTko/edit?tab=t.0
  * */

}
