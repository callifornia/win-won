package start

import ops.StringOps._
object Main {

  @main def run(): Unit = println("Hello scala 3 syntax")
//  def main(args: Array[String]): Unit = {
//    println("Hello world ...")
//  }

  val str = "Call the methonds ....."
  def check(): Unit = {
    println(str.consoleRed)
    println(str.consoleGreen)
    println(str.consoleBlue)
    println(str.length)
    println(str.consoleBlue.length)
    val a = str match {
      case "asd" => 123
      case "123" =>  321
      case _ => 000
    }
  }
}
