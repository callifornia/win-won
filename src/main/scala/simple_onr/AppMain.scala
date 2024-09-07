package simple_onr

object AppMain {

  def main(args: Array[String]): Unit = {
    (1 to 4).foreach(_ => println("Hello Scala"))

    val list = 1 :: 2 :: 3 :: Nil

    for {
      a <- list
    } yield println("Here is a simple function which print some line line n times [" + a + "]")


  }
}
