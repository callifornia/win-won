/*
  + bubbleSort
  + selectionSort
  - insertSort
  - heapSort
  - mergeSort
  - quickSort
*/

import scala.collection.mutable.{ArraySeq => MutableArray}
import scala.util.Random



def insertionSort(array: MutableArray[Int]): MutableArray[Int] = {
  (0 until array.length - 1).foreach { stepIndex =>



  }
  array
}



def selectionSort(array: MutableArray[Int]): MutableArray[Int] = {
  (0 until array.length - 1).foreach { stepIndex =>
    var minIndex = stepIndex
    var minElement = array(minIndex)

    (stepIndex to array.length - 1).foreach { nextIndex =>
      if (array(nextIndex) < array(stepIndex)) {
        minIndex = nextIndex
      }
    }

    val tmp = array(stepIndex)
    array.update(stepIndex, array(minIndex))
    array.update(minIndex, tmp)
  }
  array
}


val init = Random.shuffle((1 to 4).toList)
val result = selectionSort(MutableArray.from(init))
println("Result is: " + result)


def bubbleSort(array: MutableArray[Int]): MutableArray[Int] = {
  array.indices.foreach { _ =>
    (0 until array.length - 1).foreach { stepIndex =>

      val nextIndex = stepIndex + 1
      val stepElement = array(stepIndex)
      val nextElement = array(nextIndex)

      if (stepElement > nextElement) {
        array(stepIndex) = nextElement
        array(nextIndex) = stepElement
      }
    }
  }
  array
}

