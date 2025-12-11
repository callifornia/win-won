// bubble sort
import scala.collection.mutable.{ArraySeq => MutableArray}
val list = 1 :: 3 :: 2 :: 6 :: 0 :: 10 :: Nil




























  def bubbleSort(l: List[Int]): MutableArray[Int] = {
    val array = MutableArray.from(l)
    (0 until array.length).foreach { _ =>
      (0 until array.length - 1).foreach { stepIndex =>
        val nextIndex = stepIndex + 1
        val stepElement = array(stepIndex)
        val nextElement = array(nextIndex)
        if (nextElement < stepElement) {
          array(nextIndex) = stepElement
          array(stepIndex) = nextElement
        }
      }
    }
    array
  }








bubbleSort(1 :: 0 :: Nil)
