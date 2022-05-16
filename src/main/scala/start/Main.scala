package start

import ops.StringOps._
import CheckOne._
object Main {

  @main def run(): Unit = {

    // https://doc.akka.io/docs/akka/current/typed/actors.html
    println(s"asdasd")







  }

}

object CheckOne {
  type One = String
  opaque type Two = String
  def One(s: String): One = s
  def Two(s: String): Two = s
  def value(t: Two): String = t

  def checkTwo2(s: String): Two = { println("method one"); s}
  def checkTwo3(s: Two): String = { println("method one"); s }
}


object Lengths:
  opaque type Meters = Double
  def Meters(value: Double): Meters = value
  extension (x: Meters)
    def + (y: Meters): Meters = x + y
    def show: String = s"$x m"

def usage(): Unit =
  import Lengths.*
  val twoMeters: Meters = Meters(2.0)
  val fourMeters: Meters = twoMeters + twoMeters
  println(fourMeters.show)
  println(show(fourMeters))
