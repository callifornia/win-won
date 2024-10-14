package repeat.main.scala

object PoligonTutorial {



  /*
    [3, 4, 1, 5]

   */

  def insertSort(list: List[Int]): List[Int] = {
    def insert(acc: Int, number: Int, sortedList: List[Int]): List[Int] = {
      if (sortedList.isEmpty || number < sortedList.head) number :: sortedList
      else sortedList.head :: insert(number, sortedList.tail)
    }

    if (list.isEmpty || list.tail.isEmpty) list
    else insert(list.head, insertSort(list.tail))
  }


  def main(args: Array[String]): Unit = {

    assert(insertSort(Nil) == Nil)
    assert(insertSort(List(1)) == List(1))
    assert(insertSort(List(3,2,1)) == List(1,2,3))
    assert(insertSort(List(3,2,1,4,5,9,0)) == List(0,1,2,3,4,5,9))

//    something { session =>
//      println(session.url)
//      println(session.isActive)
//    }
  }
}
