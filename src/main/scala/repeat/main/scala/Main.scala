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

  /* Big O - показивает верхнюю межу складності виконання алгоритма в залежності від вхвідних параметрів. */

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


  



















}

