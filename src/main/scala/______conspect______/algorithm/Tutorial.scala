package ______conspect______.algorithm

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Tutorial {

  def main(args: Array[String]): Unit = {
//    priorityQueue()
//    arraylist()
//    println(binarySearch2((1 to 19).sorted.toList, 92))
//    println(linearSearch((10 to 20).toList, 12))
    println(interpolationSearch((1 to 200).toList.sorted, 195))
    println((1 to 200).toList.sorted.apply(195))
  }



  /*

       ######## DataStructure and Algorithm ########
       https://www.youtube.com/watch?v=CBYHwZcbD-s

   Big O notation

          |   O(n^2)   O(n^2)
          |     .      .                              O (n log n)            O(n^2)
          |     .     .                                 - quickSort            - insertSort
          |     .    .                                  - mergeSort            - selectionSor
          |     .   .                                   - heapSort             - bubbleSort
          |    .  .
          |   . .
          |  .
          |_______________________________



      space

          |                O(n)
          |                .                          O(1)                         O(n log(n))           O(n)
          |              .                              - insertSort                 - quickSort           - mergeSort
          |            .                                - selectionSort
          |          .        .     .   O(log n)        - bubbleSort
          |        .     .
          |      .   .
          |    .  .
          |  ......................  O (1)
          |_______________________________


   */


  // Big O notation
  {
    /* https://www.youtube.com/watch?v=ZRdOb4yR0kk

        Big O - показивает верхнюю межу складності виконання алгоритма в залежності від вхвідних параметрів
        Ми не беремо до уваги константи та "неважну" складність

        додавання - послідовність дій
        множення  - вложеність дій
        log N     - для алгоритма де на конжній ітерації береться половина елементів


        О(N^2 + N^2)            = O(N^2)
        О(N^2 + N)              = O(N^2)
        О(N + logN)             = O(N)
        O(5 * 2^N + 10*N^100)   = O(2^N)     2^N - растет гараздо бистрее чем N^100


       O (big 0): In academia, big O describes an upper bound on the time
           An algorithm that prints all the values in an array could be described as O(N),
           but it could also be described as O(N2), O(N3) ... therefore they are upper
           bounds on the runtime
            X <= 1, 000 or X <= 1,000,000. It's technically true


      `0` (big omega): In academia, `0` is the equivalent concept but for lower bound.
               Printing the values in an array is O(N) as well as O(log N) and 0(1).
               After all, you know that it won't be faster than those runtimes.


      `E` (big theta): In academia, `E` means both O and `0`. That is, an algorithm is E(N) if it is both O(N) and
                 `0`( N). 0 gives a tight bound on runtime.


       Рекурсивна фунція яка рахує сумму чисел:
            у випадку коли N = 3 функція викличе себе 3 рази
            у випадку коли N = 4 функція викличе себе 4 рази
            Швидкодія:   О (N)
     */

    def sum(n: Int): Int = n match {
      case 1 => 1
      case b => b + sum(b - 1)
    }



    /*  Функція котра пробігається по всьому масиву і додає два числа
        Швидкодія: O(N)   */
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




    // linear search
    /*
          - iterate through the collection
          - runtime complexity O(n)
          - good for small medium data sets
    * */
  def linearSearch(list: List[Int], target: Int): Option[Int] =
    list.zipWithIndex.find(_._1 == target).map(_._2)





    // binary search
    /*
      Time complexity is -> O(log n)
      Search algorithm that finds the position of a target value  within a sorted array
      Half of the array is eliminated during each "step"
    * */
  def binarySearch2(list: List[Int], target: Int): Option[Int] = {
    def recursive(low: Int, high: Int): Option[Int] =
      (low + high) / 2 match {
        case _ if high < low           => None
        case mid if list(mid) > target => recursive(low, mid - 1)
        case mid if list(mid) < target => recursive(mid + 1, high)
        case mid                       => Some(mid)
      }
    recursive(0, list.length - 1)
  }




  // interpolationSearch

  /*
    interpolationSearch - improvement over binary search
                        - guess where a value might be based on calculation probe result
                        - probe are going to be calculated on each iteration
                        - average case O(log(log(n)))
                        - O(n)
  * */
  def interpolationSearch(list: List[Int], target: Int): Option[Int] = {
    var low = 0
    var high = list.length - 1

    while (target >= list(low) && target <= list(high) && low <= high) {
      val probe = low + (high - low) * (target - list(low)) / (list(high) - list(low))
      println("probe: " + probe)
      (list(probe), target) match {
        case (p, t) if p == t   => return Some(probe)
        case (p, t) if p < t    => low = probe + 1
        case _                  => high = probe - 1
      }
    }
    None
  }



  /*
  * bubble sort - найбільший елемент переставляється в край списку O(n^2)
  *
  * */
  def bubbleSort(array: ArrayBuffer[Int]): Unit =
    (0 to array.length).foreach { i =>
      (0 to array.length - 2 - i).foreach { j =>
        if (array(j) > array(j + 1)) {
          val tmp = array(j)
          array.update(j, array(j + 1))
          array.update(j + 1, tmp)
        }
      }
    }



  /*
  * selection sort - проходить через весь масив та знаходить мінімальний елемент. В кінці ітерації пхає його наперед
  *                - O(n^2)
  *
  * */
  def selectionSort(array: ArrayBuffer[Int]): Unit =
    (0 until array.length - 1).foreach { stepIndex =>
      var indexMin = stepIndex
      (stepIndex to array.length - 1).foreach { nextIndex =>
        if (array(nextIndex) < array(indexMin)) {
          indexMin = nextIndex
        }
      }
      val tmp = array(stepIndex)
      array.update(stepIndex, array(indexMin))
      array.update(indexMin, tmp)
    }





  // Stack

  /*
    stack - LIFO data structure
            sort object vertical tower
            push() - add to the top
            pop()  - remove from the top

    uses  - browsing history
          - text editor: prev & undo button
          - stack trace
  * */


  def stackMethod(): Unit = {
    val stack = mutable.Stack.empty[Int]

    /*   add elements   */
    stack.push(10)
    stack.push(20)
    stack.push(30)
    stack.push(40)     //    4, 3, 2, 1

    stack.pop()        //    4
    stack              //       3, 2, 1

    stack.top          //    3
    stack              //       3, 2, 1

    stack.contains(30) // true
  }




  // Queue

  /*
    queue - FIFO data structure
            liner data structure
            enqueue()  - add
            dequeue()  - remove

    uses  - keyboard buffer       (when screen are not able to render)
          - printing files(or page 1, page 2) in order (should be printed in order which was sent)
  * */
  def queue(): Unit = {
    val queue = mutable.Queue.empty[Int]
    queue.enqueue(10)
    queue.enqueue(20)
    queue.enqueue(30)

    queue             // 10 20 30
    queue.dequeue()   // remove 10
    queue             // 20 30
  }




  // PriorityQueue

  /*
    PriorityQueue - FIFO data structure which hold data according to the priority (Ordering is in use to figure out priority)
                    where first element - highest priority

                    enqueue()  - add
                    dequeue()  - remove according with a highest priority

    uses  - keyboard buffer       (when screen are not able to render)
          - printing files(or page 1, page 2) in order (should be printed in order which was sent)
  * */
  def priorityQueue(): Unit = {
    val queue = mutable.PriorityQueue.empty[Int]
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.enqueue(4)
    queue.enqueue(0)

    println(queue)             // 4, 3, 2, 1, 0
    println(queue.dequeue())   // remove 4 (as a 4 has highest priority)
    println(queue)             // 3, 2, 1, 0
  }



  // LinkedList scala 2.13 does not contain such shit baby

  /*
    LinkedList        - stores Nodes in 2 parts (data + address)
                      - insertion or deletion is easy O(1)
                      - accessing/searching O(n)


    DoubledLinkedList - use 2 vals for references

    uses              - implement Stack/Queue
                      - GPS navigation
                      - music playlist
  * */


  // ArrayList

  /*
    LinkedList        - stores Nodes in 2 parts (data + address)
                      - insertion or deletion is easy O(1)
                      - accessing/searching O(n)


    DoubledLinkedList - use 2 vals for references

    uses              - implement Stack/Queue
                      - GPS navigation
                      - music playlist
  * */

  def arraylist(): Unit = {
    val arrayList = mutable.ArraySeq.empty[Int]
    val r = arrayList.appendedAll((1 to 10))
    println("=> " + arrayList)
    println("=> " + r)
  }


  //  Exercises ...
  {

    /*
    * Find all coins which in sum will be equal some number
    * For example:
    *   coins: 1, 5, 10
    *   number: 26
    *   answer: 10, 10, 5, 1
    *
    *
    * Solution above:
    * */
    val cents = 1 :: 5 :: 10 :: Nil
    val number = 26

    def function(number: Int, cents: Set[Int], acc_result: List[Int] = Nil): List[Int] =
      number match {
        case n if n <= 0 => acc_result
        case _ if cents.nonEmpty =>
          number - cents.max match {
            case 0          => acc_result :+ cents.max
            case n if n < 0 => function(number, cents - cents.max, acc_result)
            case n if n > 0 => function(number - cents.max, cents, acc_result :+ cents.max)
          }
        case _ => acc_result
      }

    function(number, cents.toSet)


    /*
    * Almost the same task but with a small changes
    * Changes: find more optimal solution
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
            case 0          => result :+ cents.max
            case n if n < 0 => function(number, cents - cents.max, result)
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
    * Bubble sort. implemented in а mutable way
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
    * Select sort
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

    def selectSorting(l: List[Int]): MutableArray[Int] = {
      val array = MutableArray.from(l)
      var minIndex = array(0)

      (0 until array.length - 1).foreach { i =>
        minIndex = i
        (i + 1 until array.length).foreach { j =>
          if (array(j) < array(i)) {
            minIndex = j
          }
        }

        if (array(minIndex) != array(i))  {
          val minElement = array(minIndex)
          val maxElement = array(i)
          array(minIndex) = maxElement
          array(i) = minElement
        }
      }
      array
    }



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
      case (Nil, list)        => list
      case (list, Nil)        => list
      case (x :: xs, y :: ys) =>
        if (x < y) x :: merge(xs, listTwo)
        else y :: merge(listOne, ys)
    }

    def mergeSort(list: List[Int]): List[Int] = list match {
      case Nil       => list
      case xs :: Nil => List(xs)
      case _         =>
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
}
