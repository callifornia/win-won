package repeat.main.interview.notes.poligon

import scala.collection.{LinearSeq, SeqMap, SortedMap, mutable}
import scala.collection.immutable.{ArraySeq, BitSet, HashMap, HashSet, ListMap, ListSet, NumericRange, Queue, SortedSet, TreeMap, TreeSet, VectorMap}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}


object DataStructure {

  def main(args: Array[String]): Unit = {

  }

  /*    https://docs.scala-lang.org/overviews/collections-2.13/performance-characteristics.html   */


  //                                                            lookup      add      remove     min
  object SetIm {
    val set:  Set[Int]           = Set(1, 2, 3)//                  C         C         C         L
      val hSet: HashSet[Int]     = HashSet(1,2,3)//                L         L         L         L
      val lSet: ListSet[Int]     = ListSet(1,2,3)//                L         L         L         L
      val sSet: SortedSet[Int]   = SortedSet(1,2,3)//              L         L         L         L
      val tSet: TreeSet[Int]     = TreeSet(1,2,3)//                Log       Log       Log       Log
      val bSet: BitSet           = BitSet(1,2,3)//                 C         L         L         C
  }


  //                                                            lookup      add      remove     min
  object MapIm {
    val hMap: HashMap[Int, String]    = HashMap(1 ->"a")//         C         C         C         L
    val tMap: TreeMap[Int, String]    = TreeMap(1 -> "a")//        Log       Log       Log       Log
    val sqMap: SeqMap[Int, String]    = SeqMap(1 -> "a")//
    val lMap: ListMap[Int, String]    = ListMap(1 -> "a")//        L         L         L         L

    /* keys are sorted according to a scala. math. Ordering */
    val sMap: SortedMap[Int, String]  = SortedMap(1 -> "a")//
    val vMap: VectorMap[Int, String]  = VectorMap(1 -> "a")//      С         С         С         L
  }




//                                                                    head	tail	apply	 update	prepend	append	insert
  object SeqIm {
    val sSeq: Seq[Int]        = Seq.apply(1)//                          C	    C	    L	     L	     C	    L
    val iSeq: IndexedSeq[Int] = IndexedSeq(1)//
      val vector: Vector[Int]        = Vector(1)//                      C	    C	    C	     C	     C	    C
      val aSeq: ArraySeq[Int]        = ArraySeq(1)//                    C	    L	    C	     L	     L	    L
      val range: Range               = Range.apply(1, 3)//              C	    C	    C
      val nRan: NumericRange[Int]    = NumericRange(1, 3, 2)//          C	    C	    C
      val str: String                = "a"//
    val lSeq: LinearSeq[Int]  = LinearSeq(1)//
      val list: List[Int]       = List(1)//                             C	    C	    L	     L	     C	    L
      val lList: LazyList[Int]  = LazyList(1)//                         C	    C	    L	     L	     C	    L
      val queue: Queue[Int]     = Queue(1)//                            C	    C	    L	     L	     L	    C


  // mutable:
  val mArray: ArrayBuffer[Int]       = ArrayBuffer(1,2,3)//             C	    L	    C	     C	     L	    C       L
  val mlist: ListBuffer[Int]         = ListBuffer(1,2,3)//              C	    L	    L	     L	     C	    C       L
  val array: mutable.ArrayDeque[Int] = mutable.ArrayDeque(1,2,3,4)//    C	    L	    C	     C	     C	    C       L

}
}
