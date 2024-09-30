import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 1 :: 4 :: 2 :: 5 :: 6 :: 3 :: Nil



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
