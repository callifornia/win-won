package repeat.main.scala

object Main {
  def main(args: Array[String]): Unit = {
    println("Notes can be found bellow ...")

  }


  /* Big O notation */
  {
    /* https://www.youtube.com/watch?v=ZRdOb4yR0kk */

    /* Big O - показивает верхнюю межу складності виконання алгоритма в залежності від вхвідних параметрів.
    *  Ми не беремо до уваги константи та "наважну" складність
    *
    *  - послідовність дій                                                => додавання
    *  - вложеність дій                                                   => множення
    *  - для алгоритма де на конжній ітерації береться половина елементів => log N
    *
    *
    *    О(N^2 + N^2)            = O(N^2)
    *    О(N^2 + N)              = O(N^2)
    *    О(N + logN)             = O(N)
    *    O(5 * 2^N + 10*N^100)   = O(2^N)     2^N - растет гараздо бистрее чем N^100
    * */


    /* Рекурсивна фунція яка рахує сумму чисел
    * У випадку коли N = 3 функція викличе себе 3 рази
    * у випадку коли N = 4 функція викличе себе 4 рази
    *
    * Швидкодія:   О (N)
    * */
    def sum(n: Int): Int = n match {
      case 1 => 1
      case b => b + sum(b - 1)
    }


    /* Функція котра пробігається по всьому масиву і додвє два числа.
    *
    *  Швидкодія: O(N)
    * */
    def sumSuqences(n: Int): Int = (0 to n).map(k => innerSumFuntion(k, k + 1)).sum

    /* швидкодія: О(1) */
    def innerSumFuntion(a: Int, b: Int): Int = a + b


    /*
    * Швидкодія: O(A + B)
    * */
    def function_1(array_1: Array[Int], array_2: Array[Int]): Unit = {
      array_1.foreach(println)
      array_2.foreach(println)
    }

    /*
    * Швидкодія: O(N^2) томущо є вложеність
    * */
    def function_2(array_1: Array[Int]): Unit = {
      array_1.foreach(a =>
        array_1.foreach(b =>
          println(a + b)))
    }


    /*
    * Швидкодія: O(A * B) томущо є вложеність
    * */
    def function_3(array_A: Array[Int], array_B: Array[Int]): Unit = {
      array_A.foreach(a =>
        array_B.foreach(b =>
          println(a + b)))
    }


    /*
    * Швидкодія: O(N)
    * */
    def function_4(array: Array[Int]): Unit = (1 to array.length / 2).foreach(println)


    /*
      O(log N) -> для алгоритма, де на кожній ітерації береться половина елементів складність буде включати log N
    */

  }


  /*
  DataStructure, Algorithm
  Scala main
     - Linerazation
     - Collections
     - Futures
  Cats
     - Type classes
     - Monad
     - Functor
     - polimorphism, monomorphism, isomorphism
  ZIO
  Akka
  Kafka


   */


// Big O notation

// Data structure and Algorithm

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
      case n if n <= 0          => result
      case _ if cents.nonEmpty  =>
        number - cents.max match {
          case 0            => result :+ cents.max
          case n if n < 0   => function(number, cents.-(cents.max), result)
          case n if n > 0   => function(number - cents.max, cents, result :+ cents.max)
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
      case n if n <= 0         => result
      case _ if cents.nonEmpty =>
        number - cents.max match {
          case 0            => result :+ cents.max
          case n if n < 0   => function(number, cents.-(cents.max), result)
          case n if n > 0   => function(number - cents.max, cents, result :+ cents.max)
        }
      case _ => result
    }

  def function_2(number: Int, cents: List[Int]): List[List[Int]] =
    cents.sorted.inits.foldLeft(List.empty[List[Int]]){ (result, cents) =>
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

    (1 until elements.length).foreach { _ =>
      (0 until elements.length - 1).foreach { stepIndex =>
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

    (0 until mutableArray.length - 1).foreach { index =>
      val indexWithLowestElem = (index + 1 until mutableArray.length).foldLeft(index) {
        (indexWithLowestElem, stepIndex) =>
          mutableArray(indexWithLowestElem) >= mutableArray(stepIndex) match {
            case true   => stepIndex
            case false  => indexWithLowestElem
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
    (0 to  mutableArray.length - 1).foreach { i =>
      var j = i
      while(j > 0  && mutableArray(j - 1) > mutableArray(j)) {
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
      if(x < y) x :: merge(xs, listTwo)
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






} // content end brakets
}

