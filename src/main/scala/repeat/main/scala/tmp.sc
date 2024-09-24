import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 1 :: 3 :: 2 :: 4 :: 6 :: 5 :: 7 :: 0 :: Nil

def function(element: List[Int]): MutableArray[Int] = {
  val mutableArray = MutableArray.from(element)

  (1 to mutableArray.length - 1).foreach { stepIndex =>
    val stepElemment = mutableArray(stepIndex)
    if (stepIndex != 0 ) {
      val stepElement = mutableArray(stepIndex)
      val prevIndex = stepIndex - 1
      val prevElement = mutableArray(prevIndex)
      if (stepElemment > prevElement) {
        mutableArray(stepIndex) = prevElement
        mutableArray(prevIndex) = stepElement
      }
    }
  }
  mutableArray
}

function(list.sorted.reverse)

