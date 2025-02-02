package ______________________tutorial______________________.poligon

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


object Poligon {

  // QuickSort
  def main(args: Array[String]): Unit = {
//    val array: ArrayBuffer[Int] = Random.shuffle(ArrayBuffer.from((1 to 9)))
    val array: ArrayBuffer[Int] = ArrayBuffer(1,9,2,8,3,7,4,6,5,0)
//    println("Init  : " + array.mkString(" "))
//    quickSort5(array, start = 0, end = array.length - 1)
//    println("Result: " + array.mkString(" "))
//    assert(array == ArrayBuffer(1,2,3,4,5,6,7,8,9))
//    println(binarySearch((10 to 20).toList.sorted, 17))
//    println(array)
//    bubbleSort(array)
//    println(array)
//    insertionSort(array)
//    println(array)
    println(array)
    quickSort6(array, 0, array.length - 1)
    println(array)
  }


//  def quickSort7(array: ArrayBuffer[Int], start: Int, end: Int): Unit =
//    if (start < end) {
//      val pivot = partition5(array, start, end)
//      quickSort7(array, start, pivot - 1)
//      quickSort7(array, pivot + 1, end)
//    }


//  def partition7(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
//    val pivot = array(end)
//    var i = start - 1
//    (start to end - 1).foreach { j =>
//      if (array(j) < pivot) {
//        i = i + 1
//        val tmp = array(i)
//        array.update(i, array(j))
//        array.update(j, tmp)
//      }
//    }
//    i = i + 1
//    val tmp = array(i)
//    array.update(i, array(end))
//    array.update(end, tmp)
//    i
//  }




  def selecttionSort3(array: ArrayBuffer[Int]): Unit =
    (0 until array.length - 1).foreach { i =>
      var indexMin = i
      (i to array.length - 1 ).foreach { j =>
        if (array(j) < array(indexMin)) {
          indexMin = j
        }
      }
      val tmp = array(i)
      array.update(i, array(indexMin))
      array.update(indexMin, tmp)
    }


  def selectionSort2(array: ArrayBuffer[Int]): Unit =
    (0 until array.length - 1).foreach { i =>
      var indexMin = i
      (i to array.length - 1).foreach { j =>
        if (array(j) < array(indexMin)) {
          indexMin = j
        }
      }
      val tmp = array(i)
      array.update(i, array(indexMin))
      array.update(indexMin, tmp)
    }












  def quickSort6(array: ArrayBuffer[Int], start: Int, end: Int): Unit =
    if (start < end) {
      val pivot = partition6(array, start, end)
      quickSort6(array, start, pivot - 1)
      quickSort6(array, pivot + 1, end)
    }


  def partition6(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
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






















  /*    0  1  2  3  4  5  6  7  8  9      */
  /*    1  9  2  8  3  7  4  6  5  0      */
  def insertionSort(array: ArrayBuffer[Int]): Unit = {
    (1 to array.length - 1).foreach { i =>
      val tmp = array(i)

      (i - 1 to 0).foreach { j =>
        if (tmp < array(j)) {
          array.update(j + 1, array(j))
        } else {
          array.update(j + 1, tmp)
        }
      }
    }
  }


  def selectionSort(array: ArrayBuffer[Int]): Unit =
    (0 until array.length - 1).foreach { i =>
      var indexMin = i
      (i to array.length - 1).foreach { j =>
        if (array(j) < array(indexMin)) {
          indexMin = j
        }
      }
      val tmp = array(i)
      array.update(i, array(indexMin))
      array.update(indexMin, tmp)
    }


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


  def binarySearch(list: List[Int], value: Int): Option[Int] = {
    def search(low: Int, hi: Int): Option[Int] = {
      (low + hi) / 2 match {
        case _ if low > hi            => None
        case mid if list(mid) > value => search(low, mid - 1)
        case mid if list(mid) < value => search(mid + 1, hi)
        case mid => Some(mid)
      }
    }
    search(0, list.length - 1)
  }


  def quickSort5(array: ArrayBuffer[Int], start: Int, end: Int): Unit =
    if (start < end) {
    val pivot = partition5(array, start, end)
    quickSort5(array, start, pivot - 1)
    quickSort5(array, pivot + 1, end)
  }


  def partition5(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
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


















  def quickSort4(array: ArrayBuffer[Int], start: Int, end: Int): Unit =
    if (start < end) {
      val pivot = partition4(array, start, end)
      quickSort4(array, start, pivot - 1)
      quickSort4(array, pivot + 1, end)
    }


  def partition4(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
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






















  def quickSort3(array: ArrayBuffer[Int], start: Int, end: Int): Unit =
    if (start <= end ) {
      val pivot = partition3(array, start, end)
      quickSort3(array, start, pivot - 1)
      quickSort3(array, pivot + 1, end)
    }


  def partition3(array: ArrayBuffer[Int], start: Int, end: Int): Int = {
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
