package ______________________tutorial______________________.poligon

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Poligon {

  // QuickSort
  def main(args: Array[String]): Unit = {
    val array: ArrayBuffer[Int] = Random.shuffle(ArrayBuffer.from((1 to 9)))
    println("Init  : " + array.mkString(" "))
    quickSort3(array, start = 0, end = array.length - 1)
    println("Result: " + array.mkString(" "))
    assert(array == ArrayBuffer(1,2,3,4,5,6,7,8,9))
  }


  //  (8,2,4,7,1,3,9,6,5)
  def quickSort(array: ArrayBuffer[Int]): ArrayBuffer[Int] = {
    var pivotI = array.length - 1
    var pivot = array(pivotI)
    println("pivot: " + pivot)

    var indexI = 0
    var indexJ = indexI + 1

    while (indexJ <= array.length) {
      if (array(indexJ) < pivot) {
        val tmp = array(indexI)
        array.update(indexI, array(indexJ))
        array.update(indexJ, tmp)
        indexI = indexI + 1
        indexJ = indexJ + 1
      } else {
        indexJ = indexJ + 1
      }
    }

    array
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
