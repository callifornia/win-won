package repeat.main.scala

object PoligonTutorial {


  case class Session(url: String, isActive: Boolean)
  def something(handle: Session => Unit) =
    handle(Session("www.asd.com", true))

  sealed trait A
  case object B extends A
  case object C extends A

  def check(s: A): Unit = s match {
    case B => println("asd")

  }

  def main(args: Array[String]): Unit = {
    println("asd")
    check(C)




//    something { session =>
//      println(session.url)
//      println(session.isActive)
//    }
  }
}
