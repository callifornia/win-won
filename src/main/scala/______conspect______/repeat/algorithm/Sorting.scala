package ______conspect______.repeat.algorithm

import scala.collection.mutable.{ArraySeq => MutableArray}
object Sorting {


//    O (n log n)            O(n^2)
//  - quickSort            - insertSort
//  - mergeSort            - selectionSor
//  - heapSort             - bubbleSort

  val array = MutableArray(6, 1, 4)

  def main(args: Array[String]): Unit = {
    println(s"Hi there ....")
    val result = QuickSort.quickSort(array)
    println("result: " + result)
  }


  //  https://www.youtube.com/shorts/-5cEMbsyMgs
  object QuickSort {
    def quickSort(array: MutableArray[Int]): MutableArray[Int] = {
      array.indices.foreach { index =>
        println("index: " + index)
        val i = index - 1
        val j = index
        val pivotIndex = array.length - 1
        val pElement = array(pivotIndex)
        val jElement = array(j)

        if (jElement <= pElement) {
          val iNext = i + 1
          val iElement = array(iNext)
          println(
            s"""
               | iNext:      $iNext
               | pIndex:     $pivotIndex
               |
               | iElement:   $iElement
               | pElement:   $pElement
               | jElement:   $jElement
               |""".stripMargin)
          array.update(iNext, jElement)
          array.update(jElement, iElement)
        }
      }
      array
    }
  }


  object MergeSort {

  }


  object HeapSort {

  }


  object InsertSort {

  }


  object SelectionSort {

  }


  object BubbleSort {

  }
}


