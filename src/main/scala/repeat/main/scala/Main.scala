package repeat.main.scala

object Main {
  def main(args: Array[String]): Unit = {

    println("Going to start repeat Scala")
  }
/*


  Big O notation
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


  //    Big O notation
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

