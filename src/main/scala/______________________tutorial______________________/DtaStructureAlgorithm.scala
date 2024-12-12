package ______________________tutorial______________________

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object DtaStructureAlgorithm {

  /*

       ######## DataStructure and Algorithm ########

   Big O notation

          |   O(n^2)   O(n^2)
          |     .      .               O (n log n)            O(n^2)
          |     .     .                   - quickSort            - insertSort
          |     .    .                    - mergeSort            - selectionSor
          |     .   .                     - heapSort             - bubbleSort
          |    .  .
          |   . .
          |  .
          |_______________________________



      space

          |                O(n)
          |                .                         O(n)             O(n log(n))           O(1)
          |              .                             - mergeSort       - quickSort          - insertSort
          |            .                                                                      - selectionSort
          |          .        .     .   O(log n)                                              - bubbleSort
          |        .     .
          |      .   .
          |    .  .
          |  ......................  O (1)
          |_______________________________


   */


  // QuickSort
  def main(args: Array[String]): Unit = {
    val array: ArrayBuffer[Int] = Random.shuffle(ArrayBuffer.from((1 to 9)))
    println("Init  : " + array.mkString(" "))
    quickSort2(array, start = 0, end = array.length - 1)
    println("Result: " + array.mkString(" "))
    assert(array == ArrayBuffer(1,2,3,4,5,6,7,8,9))
  }

  def quickSort2(array: ArrayBuffer[Int], start: Int, end: Int): Unit =
    if (start <= end) {
      val pivot = partition2(array, start, end)

      quickSort2(array, start, pivot - 1)
      quickSort2(array, pivot + 1, end)
    }


  def partition2(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
    val pivot = array(end)
    var i = start - 1
    (start to end - 1).foreach { j =>
      if (array(j) <= pivot) {
        i = i + 1
        val tmp = array(i)
        array.update(i, array(j))
        array.update(j, tmp)
      }
    }
    i = i + 1
    val tmp = array(i)
    array.update(i, array(end))
    array.update(end, tmp)
    i
  }



  def partition(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
    val pivot = array(end)
    var i = start - 1
    (start to end - 1).foreach { j =>
      if (array(j) < pivot) {
        i = i + 1
        val tmp = array(i)
        array.update(i, array(j))
        array.update(j, tmp)
      }
    }
    i = i + 1
    val tmp = array(i)
    array.update(i, array(end))
    array.update(end, tmp)
    i
  }



}
