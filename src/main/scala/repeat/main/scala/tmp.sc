import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 1 :: 3 :: 2 :: 4 :: 6 :: 5 :: 7 :: 0 :: Nil

def function(elements: List[Int]): MutableArray[Int] = {
  val mutableArray = MutableArray.from(elements)

  (1 until elements.length).foreach { index =>
    (0 until elements.length - 1).foreach { stepIndex =>
      val nextIndex = stepIndex + 1
      val stepElement = mutableArray(stepIndex)
      val nextElement = mutableArray(nextIndex)

      if (stepElement > nextElement) {
        mutableArray(nextIndex) = stepElement
        mutableArray(stepIndex) = nextElement
      }
    }
  }
  mutableArray
}

function(list)

