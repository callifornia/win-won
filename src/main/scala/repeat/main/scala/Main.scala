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






}





}

