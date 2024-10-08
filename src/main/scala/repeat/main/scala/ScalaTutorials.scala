package repeat.main.scala

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
  byValueFunction(3 + 2) // 3 + 2 evaluated before method "byValueFunction" are going to be called

  def byNameFunction(x: => Int): Int = x + 1
  byNameFunction(3 + 2) // 3 + 2 are going to be evaluated when it's going to be used inside that function "byNameFunction".
    // in other words => call by need


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
  *  That pattern is named as "call by need"
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





}




