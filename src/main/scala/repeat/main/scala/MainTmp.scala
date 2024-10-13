package repeat.main.scala
import scala.collection.mutable.{ArraySeq => MutableArray}


object MainTmp {

  val list = 1 :: 4 :: 0 :: 2 :: 5 :: 6 :: 3 :: Nil

  def main(args: Array[String]): Unit = {
    println(quickSort(MutableArray.from(list)))
  }



  /*
  * ............
  *
  * */
  def splitAndSort(list: List[Int]) = list match {
    case  Nil => list
    case x :: y :: xs =>
    case _ =>
//      val (listOne, listTwo) = splitAndSort(list.splitAt(list.length / 2))
      ???
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


/*
* import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 1 :: 4 :: 2 :: 5 :: 6 :: 3 :: Nil



val array_1 = MutableArray.from(1 :: 2 :: Nil)
val array_2 = MutableArray.from(3 :: 4 :: Nil)


array_2 ++ array_1

def quickSort(array: MutableArray[Int]): MutableArray[Int] = {
  val pivot = array(array.length - 1)
  var swapMarker = 0
  (0 to array.length).foreach { currentIndex =>
    if (array(currentIndex) < pivot ) {
      swapMarker = swapMarker + 1
    } else {
      swapMarker = swapMarker + 1
      val currentElement = array(currentIndex)
      val swapElement = array(swapMarker)
      array(currentIndex) = swapElement
      array(swapMarker) = currentElement
    }
  }
  array
}


quickSort(MutableArray.from(list))
*
* */

