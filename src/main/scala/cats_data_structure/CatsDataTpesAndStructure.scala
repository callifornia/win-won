package cats_data_structure
import cats.Eval
import cats.data._


object CatsDataTpesAndStructure {
  def main(args: Array[String]): Unit = {


  }


  object ChainData {
    /*
    * Fairly simple data structure compared to something like Vector
    *
    * - it's basically wrapper:     Wrap[A](seq: immutable.Seq[A])
    * - supports CONSTANT TIME APPENDING and PREPENDING
    * - it's builds an UNBALANCED TREE of Appends.
    * - always allow iteration in LINER TIME.
    *
    *   Chain || NonEmptyChain
    * */

    val _: Chain[Int] = Chain(1,2,3,4)
  }


  object EvalDataType {
    /*
    * Eval is a data type for controlling synchronous evaluation
    * memoization and laziness
    *
    * memoized evaluation ->  evaluates an expression only once and then remembers that value
    * val                 -> evaluates eagerly and also memoizes the result.
    * lazy val            -> is both lazy and memoized,
    * def                 -> is lazy, but not memoized, since the body will be evaluated on every call.
    * guarantees stack-safety

    * Eval is able to express all of these evaluation strategies and allows us to chain computations
    * using its Monad instance.
    * */

    val a: Eval[Int] = Eval.now(1 + 1)

    val aa: Int      = a.value

    val b: Eval[Int] = Eval.later(1 + 1)

    // lazy evaluation, but without memoization
    val c: Eval[Int] = Eval.always(1 + 1)

    val d: Eval[Boolean] =
      Eval.now{println("now"); 1}
        .flatMap{_ => Eval.later{println("later"); "a"}}
        .flatMap(_ => Eval.always{println("always"); true})

    // will print: "now"
    a.value
    // "later", "always"
  }
}






















