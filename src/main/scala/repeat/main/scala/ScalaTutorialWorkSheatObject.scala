package repeat.main.scala

object ScalaTutorialWorkSheatObject {


  trait DLLList[+T] {
    def value: T
    def next: DLLList[T]
    def prev: DLLList[T]
    def append[S >: T](element: S): DLLList[S]
    def prepend[S >: T](element: S): DLLList[S]

    def updateNext[S >: T](newNext: => DLLList[S]): DLLList[S]
    def updatePrev[S >: T](newPrev: => DLLList[S]): DLLList[S]

  }


  object DLLEnpty extends DLLList[Nothing] {
    override def value: Nothing = throw new NoSuchElementException("There are no element")
    override def next: DLLList[Nothing] = throw new NoSuchElementException("There are no element")
    override def prev: DLLList[Nothing] = throw new NoSuchElementException("There are no element")

    override def append[S >: Nothing](element: S) = new DLLCons(element, DLLEnpty, DLLEnpty)
    override def prepend[S >: Nothing](element: S) = new DLLCons(element, DLLEnpty, DLLEnpty)

    override def updateNext[S >: Nothing](newNext: => DLLList[S]) = this
    override def updatePrev[S >: Nothing](newPrev: => DLLList[S]) = this

  }



  class DLLCons[+T](override val value: T,
                    p: => DLLList[T],
                    n: => DLLList[T]) extends DLLList[T] {

    override lazy val next: DLLList[T] = n
    override lazy val prev: DLLList[T] = p


    override def updatePrev[S >: T](newPrev: => DLLList[S]): DLLList[S] = {
      lazy val result: DLLList[S] = new DLLCons(value, newPrev, n.updatePrev(result))
      result
    }


    override def updateNext[S >: T](newNext: => DLLList[S]): DLLList[S] = {
      lazy val result: DLLList[S] = new DLLCons(value, p.updateNext(result), newNext)
      result
    }



    override def append[S >: T](element: S): DLLList[S] = {
      lazy val result: DLLList[S] = new DLLCons(value, p.updateNext(result), n.append(element).updatePrev(result))
      result
    }


    override def prepend[S >: T](element: S): DLLList[S] = {
      lazy val result: DLLList[S] = new DLLCons(value, p.prepend(element).updateNext(result), n.updatePrev(result))
      result
    }

  }


  def main(args: Array[String]): Unit = {

    // 3 - 1 - 2 - 4
    val list = DLLEnpty.prepend(1).append(2).prepend(3).append(4)
    assert(list.value == 1)
    assert(list.next.value == 2)
    assert(list.next.prev == list)
    assert(list.prev.value == 3)
    assert(list.prev.next == list)
    assert(list.next.next.value == 4)
    assert(list.next.next.prev.prev == list)
  }
}
