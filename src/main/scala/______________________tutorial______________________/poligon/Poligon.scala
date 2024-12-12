package ______________________tutorial______________________.poligon

import scala.collection.mutable.ArrayBuffer

object Poligon {
  def main(args: Array[String]): Unit = {
    //    val array: ArrayBuffer[Int] = Random.shuffle(ArrayBuffer(1,2,3,4,5,6,7,8,9,10))
    val array: ArrayBuffer[Int] = ArrayBuffer(8, 2, 4, 7, 1, 3, 9, 6, 5)
    println("Init array: " + array)
    println(quickSort(array))
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
}
