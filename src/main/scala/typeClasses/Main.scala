package typeClasses
import EncoderSyntax._
import EncoderImpl._
import scala.util.Try
import DecoderSyntax._
import DecoderImpl._

object Main {
  @main def run(): Unit = {
//    "Foo".encode :: 123.encode :: Nil foreach println
//    Foo(123).encode
//    Foo(123).encode.decode[Foo] :: Nil foreach println

    A(B(C(true))).encode :: Nil foreach println
    A(B(C(true))).encode.decode[A] :: Nil foreach println
  }
}
case class A(b: B)
case class B(c: C)
case class C(d: Boolean)

case class Foo(a: Int)

trait Decoder[T] {
  def decode(str: String): Option[T]
}

object DecoderSyntax {
  def decode[T](value: String)(implicit decoder: Decoder[T]): Option[T] = decoder.decode(value)

  implicit class DecoderOps(value: String) {
    def decode[T](implicit decoder: Decoder[T]): Option[T] = decoder.decode(value)
  }
}

object DecoderImpl {

  implicit object CDecoder extends Decoder[C] {
    def decode(str: String): Option[C] =
      Try(str.stripPrefix(EncoderImpl.str + "C(").stripSuffix(")"))
        .toOption
        .map(v => C(v.toBoolean))
  }

  implicit object BDecoder extends Decoder[B] {
    def decode(str: String): Option[B] =
      Try(str.stripPrefix(EncoderImpl.str + "B(").stripSuffix(")"))
        .toOption
        .flatMap(_.decode[C].map(B))
  }

  implicit object ADecoder extends Decoder[A] {
    def decode(str: String): Option[A] =
      Try(str.stripPrefix(EncoderImpl.str + "A(").stripSuffix(")"))
        .toOption
        .flatMap(_.decode[B].map(A))
  }

  implicit object FooDecoder extends Decoder[Foo] {
    def decode(str: String): Option[Foo] =
      Try(str.stripPrefix(EncoderImpl.str + "Foo(").stripSuffix(")").toIntOption)
        .toOption
        .flatten
        .map(Foo)
  }

  implicit object IntDecoder extends Decoder[Int] {
    def decode(str: String): Option[Int] = str.stripPrefix(EncoderImpl.str).toIntOption
  }

  implicit object StringDecoder extends Decoder[String] {
    def decode(str: String): Option[String] = Some(str.stripPrefix(EncoderImpl.str))
  }
}




trait Encoder[T] {
  def encode(v: T): String
}

object EncoderSyntax {
  def encode[T](v: T)(implicit encoder: Encoder[T]): String = encoder.encode(v)

  implicit class EncoderOps[T](value: T) {
    def encode(implicit encoder: Encoder[T]): String = encoder.encode(value)
  }
}

object EncoderImpl {
  val str = "Encoded: "

  implicit object CEncoder extends Encoder[C] {
    def encode(v: C): String = str + v.getClass.getSimpleName + "(" + v.d + ")"
  }

  implicit object BEncoder extends Encoder[B] {
    def encode(v: B): String = str + v.getClass.getSimpleName + "(" + v.c.encode + ")"
  }

  implicit object AEncoder extends Encoder[A] {
    def encode(v: A): String = str + v.getClass.getSimpleName + "(" + v.b.encode + ")"
  }

  implicit object FooEncoder extends Encoder[Foo] {
    def encode(v: Foo): String = str + v.getClass.getSimpleName
  }

  implicit object IntEncoder extends Encoder[Int] {
    def encode(v: Int): String = str + v
  }

  implicit object StringEncoder extends Encoder[String] {
    def encode(v: String): String = str + v
  }

}


