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

    val array: ArrayBuffer[Int] = Random.shuffle(ArrayBuffer.from((1 to 10)))
    println("Init array: " + array.mkString(","))
    val result = quickSort(array, start = 0, end = array.length - 1)
    println("Result: " + result)
  }


  def quickSort(array: ArrayBuffer[Int], start: Int, end: Int): Unit =
    if (start <= end) {
      println(s"start: $start  end: $end")
      val pivot = partition(array, start, end)
      println(pivot)

      quickSort(array, start, pivot - 1)
      quickSort(array, pivot + 1, end)
    }


  def partition(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
    val pivot = array(end)
    println("array: " + array)
    println("pivot: " + pivot)
    var i = start - 1
    Range(start, end - 1).foreach{ j =>
      if (array(j) < pivot) {
        i = i + 1
        val tmp = array(i)
        array.update(i, array(j))
        array.update(j, tmp)
      }
    }
    i
  }





}
