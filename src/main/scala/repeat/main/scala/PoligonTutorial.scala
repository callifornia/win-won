package repeat.main.scala

object PoligonTutorial {


  case class Session(url: String, isActive: Boolean)
  def something(handle: Session => Unit) =
    handle(Session("www.asd.com", true))


  def main(args: Array[String]): Unit = {
    something { session =>
      println(session.url)
      println(session.isActive)
    }
  }
}
