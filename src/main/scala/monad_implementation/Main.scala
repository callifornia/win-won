package monad_implementation

object Main {
  def main(args: Array[String]): Unit = {




    trait Foo2 {
      def sign(): Unit
    }

    trait Bar { self: Foo2 =>
      def song(): Unit = self.sign()

    }


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
