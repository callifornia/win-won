package repeat.main.scala
import scala.collection.mutable.{ArraySeq => MutableArray}


object MainTmp {

  val list = 1 :: 4 :: 2 :: 5 :: 6 :: 3 :: Nil

  def main(args: Array[String]): Unit = {
    println(quickSort(MutableArray.from(list)))
  }

  def quickSort(array: MutableArray[Int]): MutableArray[Int] = {
    val pivot = array(array.length - 1)
    var swapMarker = 0
    (0 until array.length).foreach { currentIndex =>
      val currentElement = array(currentIndex)
      if (currentElement < pivot) {
        if (swapMarker <= currentIndex) {
          val currentElement = array(currentIndex)
          val swapElement = array(swapMarker)
          array(currentIndex) = swapElement
          array(swapMarker) = currentElement
          swapMarker = swapMarker + 1
        }
      } else if (currentElement == pivot) {
        val currentElement = array(currentIndex)
        val swapElement = array(swapMarker)
        array(swapMarker) = currentElement
        array(currentIndex) = swapElement
      }
    }
    array
  }
}

