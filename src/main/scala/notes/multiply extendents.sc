
object Check extends Foo with Foo2 with Foo3 {
  def ds() = super.foo()
}

trait Mainsa {
  def foo(): String = "Mainsa"
}

trait Foo extends Mainsa {
  override def foo(): String = "Foo"
}

trait Foo2 extends Mainsa {
  override def foo(): String = "Foo2"
}

trait Foo3 extends Mainsa {
  override def foo(): String = super.foo()
}

trait FooTrait
object FooOb extends FooTrait
case class FooClass2(c: String) extends FooTrait
type Objfgfg = String

case class FooClass(someValue: String)
object Foo {
  val objectValue: String = "asd"
}


val str = "Call the methonds ....."
def check(acd: String): Unit = {
  FooClass("asd").someValue
  val cc = Foo.objectValue

  val d = str.toUpperCase()
//  println(str.consoleRed)
//  println(str.consoleGreen)
  //    println(str.consoleBlue)
  println(str.length)
  //    println(str.consoleBlue.length)
  val a = str match {
    case "asd" => 123
    case "123" =>  321
    case _ => 0
  }
}