import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 1 :: 3 :: 2 :: 4 :: 6 :: 5 :: 7 :: 0 :: Nil
//val list = 1 :: 3 :: 2 :: 0 :: Nil



def function(elements: List[Int]): MutableArray[Int] = {

  val mutableArray = MutableArray.from(elements)
  type Index = Int
  type Element = Int

  (0 until mutableArray.length - 1).foreach { index =>
    val lowestIndex = (index + 1 until mutableArray.length).foldLeft[Index](index) {
      (result, stepIndex) =>
        mutableArray(stepIndex) < mutableArray(index) match {
          case true   => stepIndex
          case false  => index
        }
    }

    /* swap if lowest element was found */
    if (lowestIndex != index) {
      val stepElement = mutableArray(index)
      mutableArray(index) = mutableArray(lowestIndex)
      mutableArray(lowestIndex) = stepElement
    }
  }

  mutableArray
}

function(list.sorted.reverse)

