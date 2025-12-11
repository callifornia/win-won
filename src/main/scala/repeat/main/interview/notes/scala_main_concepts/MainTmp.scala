package repeat.main.interview.notes.scala_main_concepts

import scala.collection.mutable.{ArraySeq => MutableArray}


object MainTmp {

  val list = 1 :: 4 :: 0 :: Nil

  def main(args: Array[String]): Unit = {
    println(selectSorting(list))
  }


  def selectSorting(l: List[Int]): MutableArray[Int] = {
    val array = MutableArray.from(l)
    var minIndex = array(0)

    (0 until array.length - 1).foreach { i =>
      minIndex = i
      (i + 1 until array.length).foreach { j =>
        if (array(j) < array(i)) {
          minIndex = j
        }
      }

      if (array(minIndex) != array(i))  {
        val minElement = array(minIndex)
        val maxElement = array(i)
        array(minIndex) = maxElement
        array(i) = minElement
      }
    }
    array
  }
}