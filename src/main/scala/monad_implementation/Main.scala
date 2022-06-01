package monad_implementation

object Main {
  def main(args: Array[String]): Unit = {

    case class Foo(str: String) {
      def asdf(): String = str + "method was called"
    }
    object Foo {

    }
    implicit def transform(value: String): Foo = Foo(value)

    val result = "asd".asdf()
    println(result)


  }
}
