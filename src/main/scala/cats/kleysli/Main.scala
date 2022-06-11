package cats.kleysli
import cats.FlatMap
import cats.implicits._


object Main {

  case class Kleisli[F[_], In, Out](run: In => F[Out]) {
    def andThen[Out2](k: Kleisli[F, Out, Out2])(implicit f: FlatMap[F]): Kleisli[F, In, Out2] =
      Kleisli[F, In, Out2](in => run.apply(in).flatMap(out => k.run(out)))
  }


  def main(args: Array[String]): Unit = {
    /*
    * We do have:
    *   f: Int => String
    *   g: String => Double
    * So we can compose both functions in a way: f andThen g
    *
    *   h: String => Option[Int]
    *   p: Int => Option[Double]
    * We can not write: h andThen p, here is where Kleisli comes to play
    *
    * */
    val a: String => Option[Int] = _.toInt.some
    val b: Int => Option[Double] = _.toDouble.some

//    val c = a andThen b
    val cc: Kleisli[Option, String, Double] = Kleisli(a) andThen Kleisli(b)
    println("Result is: " + cc.run("123"))
  }
}
