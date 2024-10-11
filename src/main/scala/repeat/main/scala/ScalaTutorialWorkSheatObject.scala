package repeat.main.scala

object ScalaTutorialWorkSheatObject {




  def customWhile(bool: Boolean)(function : => Unit): Unit =
    bool match {
      case true =>
        function
        customWhile(bool)(function)
      case false => ()
    }



  def main(args: Array[String]): Unit = {

    val a = 123
    customWhile(a > 0) {
      println("Hello world ...")
    }


  }

}

