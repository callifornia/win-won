package repeat.main.scala_main_concepts

object ScalaTutorialWorkSheatObject {



  case class Session(url: String, isAlive: Boolean)

  def builder(handle: Session => Unit): Unit = {
    handle(Session("www.trump.ua", true))
  }

  builder {session =>
    println(session.url)
    println(session.isAlive)
  }

  def main(args: Array[String]): Unit = {

  }

}

