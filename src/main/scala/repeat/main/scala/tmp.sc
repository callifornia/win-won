import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 1 :: 3 :: 2 :: 4 :: 6 :: 5 :: 7 :: 0 :: Nil
def function(elements: List[Int]): MutableArray[Int] = {

  val mutableArray = MutableArray.from(elements)

  (0 until mutableArray.length - 1).foreach { index =>
    val lowestIndex = (index + 1 until mutableArray.length).foldLeft(index) {
      (lowestIndex, stepIndex) =>
        mutableArray(lowestIndex) >= mutableArray(stepIndex) match {
          case true   => stepIndex
          case false  => lowestIndex
        }
    }

    /* swap if lowest element was found otherwise ignore */
    if (lowestIndex != index) {
      val stepElement = mutableArray(index)
      mutableArray(index) = mutableArray(lowestIndex)
      mutableArray(lowestIndex) = stepElement
    }
  }

  mutableArray
}

function(list.sorted.reverse)

