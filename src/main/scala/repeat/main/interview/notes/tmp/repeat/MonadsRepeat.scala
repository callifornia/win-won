package repeat.main.interview.notes.tmp.repeat

import scala.concurrent.Future

object MonadsRepeat {
  def main(args: Array[String]): Unit = {
    val numbers = List(1,2,3)
    val charsList = List('a', 'b', 'c')

    val r1 = for {
      a <- numbers
      c <- charsList
    } yield (a, c)

    val r2 = numbers.flatMap(a => charsList.map(c => (a, c)))

    println(r1)
    println(r2)

    val a1 = Option(1)
    val a2 = Option("b")
    val r3 = a1.flatMap(a => a2.map(c => (a, c)))
    val r4 =
      for {
        a <- a1
        c <- a2
      } yield (a, c)

    println(r3)
    println(r4)

    (a1, a2) match {
      case (Some(a), Some(b)) => Some(a, b)
      case _                  => None
    }

    import scala.concurrent.ExecutionContext.Implicits.global
    val f1 = Future(1)
    val f2 = Future(1)
    val fr1 = f1.flatMap(ff1 => f2.map(ff2 => (ff1, ff2)))
    val fr2 =
      for {
        f_1 <- f1
        f_2 <- f2
      } yield (f_1, f_2)


    /* Monad Option */
    import cats.Monad
    import cats.instances.option._

    val anOptionMonad = Monad[Option]
    val anOptionValue = anOptionMonad.pure(123)
    val anOptionResult = anOptionMonad.flatMap(anOptionValue)(x => Some(x * 2))
    println(anOptionResult)


    /* Monad List */
    val aListMonad = Monad[List]
    val aListValue = aListMonad.pure(3)
    val aListResult = aListMonad.flatMap(aListValue)(x => List(x / 2))
    println(aListResult)


    /* Monad Future */
    val aFutureMonad = Monad[Future]
    val aFutureValue = aFutureMonad.pure(114)
    val aFutureResult = aFutureMonad.flatMap(aFutureValue)(x => Future(x * 2))
    aFutureResult.onComplete(println)
    Thread.sleep(9000)

  }
}
