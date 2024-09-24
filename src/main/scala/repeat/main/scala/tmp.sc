import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 1 :: 3 :: 2 :: 4 :: 6 :: 5 :: 7 :: 0 :: Nil
def function(elements: List[Int]): MutableArray[Int] = {

  val mutableArray = MutableArray.from(elements)

  (1 until mutableArray.length).foreach { stepIndex =>
    var stepElement = mutableArray(stepIndex)

    ???
  }

  mutableArray
}

function(list.sorted.reverse)

