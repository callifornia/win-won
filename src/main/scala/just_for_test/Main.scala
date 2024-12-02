package just_for_test

import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {

    readFiles.foreach(println(_))
  }



  def readFiles(): Seq[Foo] =
    Source.fromFile("logs/offerid.log").getLines().map(e => Foo(e)).toSeq

  case class Foo(value: String)

}
