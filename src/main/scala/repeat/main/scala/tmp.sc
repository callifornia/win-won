import scala.collection.mutable.{ArraySeq => MutableArray}

val list = 7 :: 6 :: 5 :: 4 :: 10 :: 3 :: 13 :: 2 :: 1 :: 0 :: Nil

def function(element: List[Int]): MutableArray[Int] = {
  val mutableArray = MutableArray.from(element)
  (0 to  mutableArray.length - 1).foreach { i =>
    var j = i
    while(j > 0  && mutableArray(j - 1) > mutableArray(j)) {
      val a = mutableArray(j - 1)
      val b = mutableArray(j)
      mutableArray(j - 1) = b
      mutableArray(j) = a
      j = j - 1
    }
  }

  mutableArray
}

function(list)



