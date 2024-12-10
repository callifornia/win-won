package repeat.main.interview.notes.poligon

import scala.collection.{LinearSeq, SeqMap, SortedMap, mutable}
import scala.collection.immutable.{ArraySeq, BitSet, HashMap, HashSet, ListMap, ListSet, NumericRange, Queue, SortedSet, TreeMap, TreeSet, VectorMap}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}




object DataStructure {

  def main(args: Array[String]): Unit = {

    val sortedSet: SortedSet[Int] = SortedSet(2, 1, 3, 4, 5, 9, 0)
    println(sortedSet)

    (1 to 10).iterator.map(println).take(3).toList
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
    val sMap: SortedMap[Int, String]  = SortedMap(1 -> "a")
    val vMap: VectorMap[Int, String]  = VectorMap(1 -> "a")//      С         С         С         L
  }




  //                                                                  head	tail	apply	 update	prepend	append	insert
  object SeqIm {
    val sSeq: Seq[Int]        = Seq.apply(1)//                          C	    C	    L	     L	     C	    L
    val iSeq: IndexedSeq[Int] = IndexedSeq(1)//
      val vector: Vector[Int]        = Vector(1)//                      C	    C	    C	     C	     C	    C
      val aSeq: ArraySeq[Int]        = ArraySeq(1)//                    C	    L	    C	     L	     L	    L
      val range: Range               = Range.apply(1, 3)//              C	    C	    C
      val nRan: NumericRange[Int]    = NumericRange(1, 3, 2)//          C	    C	    C
      val str: String                = "a"
    val lSeq: LinearSeq[Int]  = LinearSeq(1)//
      val list: List[Int]            = List(1)//                        C	    C	    L	     L	     C	    L
      val lList: LazyList[Int]       = LazyList(1)//                    C	    C	    L	     L	     C	    L
      val queue: Queue[Int]          = Queue(1)//                       C	    C	    L	     L	     L	    C


  // mutable:
  val mArray: ArrayBuffer[Int]       = ArrayBuffer(1,2,3)//             C	    L	    C	     C	     L	    C       L
  val mlist: ListBuffer[Int]         = ListBuffer(1,2,3)//              C	    L	    L	     L	     C	    C       L
  val array: mutable.ArrayDeque[Int] = mutable.ArrayDeque(1,2,3,4)//    C	    L	    C	     C	     C	    C       L





  /*
  *   SORTED_SET
  *   - A SortedSet is a set that produces its elements (using iterator) in a given ordering
  *     (which can be freely chosen at the time the set is created)
  *
  *
  *   - Representation of a SORTED_SET is an ORDERED_BINARY_TREE all
  *   - elements in the left subtree of a node are smaller than all elements in the right subtree
  *
  *   - simple in order traversal can return all tree elements in increasing order
  *
  *                           input:  SortedSet(2, 1, 3, 4, 5, 9, 0)
  *                          output:  TreeSet(0, 1, 2, 3, 4, 5, 9)
  *
  *   - Scala’s class TREE_SET uses a RED_BLACK_TREE implementation to maintain this ordering
  *
  *
  *
  *
  *   BIT_SET
  *   - sets of non-negative integer elements
  *
  *
  *
  *
  *   ITERATOR
  *   - an iterator is not a collection, but rather a way to access the elements of a collection one by one
  *   - if no more elements to return - throw a NoSuchElementException
  *   - "next" and "hasNext"
  *
  *             (1 to 10).iterator.map(println).take(3).toList
  *                   print only "3"
  *
  *             (1 to 10).map(println).take(3).toList
  *                   print "10"
  *
  *
  *
  *
  *   ARRAY
  *   Array is a special kind of collection in Scala
  *   - scala arrays correspond one-to-one to Java arrays
  *   - scala Array[Int] is represented as a Java int[],
  *           Array[Double] is represented as a Java double[]
  *           Array[String] is represented as a Java String[]
  *
  *   - scala arrays can be generic
  *   - you can pass an Array[T] where a Seq[T] is required
  *   - scala arrays also support all sequence operations
  *   - implicit conversion from arrays to ArraySeq
  *
  *
  *
  *
  *
  *
  * */








}
}
