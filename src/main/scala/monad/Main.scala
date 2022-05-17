package monad

object Main {
  @main def run(): Unit = {

    CustomMonad("foo")
      .flatMap(v => CustomMonad(v.toUpperCase))
      .flatMap(v => CustomMonad(v.toLowerCase)) :: Nil foreach println



  }
}
/*
*  Monad(x).flatMap(f) == f(x)
*  Monad(x).flatMap(x => Monad(x)) == Monad(x)
*  Monad(x).flatMap(f).flatMap(g) == Monad(x).flatMap(x => f(x).flatMap(g))
*
*
* */
case class CustomMonad[+T](private val value: T ) {
  def get(): T = value
  def flatMap[S](function: T => CustomMonad[S]): CustomMonad[S] =
    function(value)
}