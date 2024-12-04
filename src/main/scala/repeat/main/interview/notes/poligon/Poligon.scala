package repeat.main.interview.notes.poligon

import repeat.main.interview.notes.poligon.InterviewStuff.Free.pure


object InterviewStuff {

  case class SafeValue[+T](private val value: T) {
    def get = synchronized {
      value
    }

    def transform[A](function: T => SafeValue[A]): SafeValue[A] =
      function.apply(value)
  }

  def wrapValue[T](value: T): SafeValue[T] = SafeValue(value)
  // extract
  val extractedValue = wrapValue("Hello world").get
  // transform
  val transformedExtractedValue: String = extractedValue.toUpperCase()
  // wrap
  val wrappedTransformedExtractedValue: SafeValue[String] = SafeValue(transformedExtractedValue)


  trait Monad[M[_]] {
    def pure[A](value: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  }


  object Monad {
    def apply[M[_]](implicit monad: Monad[M]): Monad[M] = monad
  }


  /*  ALGEBRA  */
  trait DBIO[A]
  case class Create[A](key: String, value: A) extends DBIO[Unit]
  case class Update[A](key: String, value: A) extends DBIO[Unit]
  case class Read[A](key: String) extends DBIO[A]
  case class Delete(key: String) extends DBIO[Unit]

  type DBIOMonad[A] = Free[DBIO, A]

  trait Free[M[_], A] {
    def flatMap[B](f: A => Free[M, B]): Free[M, B] = FlatMap(this, f)
    def map[B](f: A => B): Free[M, B] = flatMap(a => pure(f(a)))

    def foldMap[G[_]: Monad](transformation: M ~> G): G[A] = this match {
      case Pure(value)                   => Monad[G].pure(value)
      case Suspend(value: M[A])        => transformation.apply(value)
      case FlatMap(fa: Free[M, Any], ff) =>
        Monad[G].flatMap(fa.foldMap(transformation))(a => ff(a).foldMap(transformation))
    }
  }


  case class FlatMap[M[_], A, B](fa: Free[M, A], f: A => Free[M, B]) extends Free[M, B]
  case class Pure[M[_], A](value: A) extends Free[M, A]
  case class Suspend[M[_], A](ma: M[A]) extends Free[M, A]


  object Free {
    def pure[M[_], A](value: A): Free[M, A] = Pure(value)
    def liftM[M[_], A](value: M[A]): Free[M, A] = Suspend(value)
  }

  trait ~>[G[_], F[_]] { def apply[A](value: G[A]): F[A] }


  implicit object MonadIO extends Monad[IO] {
    override def pure[A](value: A): IO[A] = IO(() => value)
    override def flatMap[A, B](ma: IO[A])(f: A => IO[B]): IO[B] =
      IO(() =>  f(ma.unsafeRun()).unsafeRun())
  }


  def create[A](key: String, value: A): DBIOMonad[Unit] = Free.liftM[DBIO, Unit](Create(key, value))
  def update[A](key: String, value: A): DBIOMonad[Unit] = Free.liftM[DBIO, Unit](Update(key, value))
  def read[A](key: String): DBIOMonad[A]                = Free.liftM[DBIO, A](Read(key))
  def delete(key: String): DBIOMonad[Unit]              = Free.liftM[DBIO, Unit](Delete(key))

  def myLittleProgram: Free[DBIO, Unit] =
    for {
      _       <- create("Hi", 32)
      value   <- read[String]("Hi")
      _       <- update("Hi", 64)
      _       <- delete("Hi")
      value2  <- read[String]("Hi")
      _       = println("value: " + value)
      _       = println("value2: " + value2)
    } yield ()


  /* smart constructor */
  case class IO[A](unsafeRun: () => A)
  object IO {
    def create[A](value: => A): IO[A] = IO(() => value)
  }

  val mutableMap: scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map.empty[String, String]

  val dbo2io: DBIO ~> IO = new (DBIO ~> IO) {
    override def apply[A](value: DBIO[A]): IO[A] = value match {
      case Create(key, value) => IO.create{println("create..."); mutableMap.addOne((key, value.toString)); () }
      case Update(key, value) => IO.create{println("update..."); mutableMap.addOne((key, value.toString)); () }
      case Read(key)          => IO.create{println("read..."); mutableMap.getOrElse(key, "").asInstanceOf[A]}
      case Delete(key)        => IO.create{println("delete..."); mutableMap.remove(key); ()}
    }
  }



  def main(args: Array[String]): Unit = {

    val result: IO[Unit] = myLittleProgram.foldMap(dbo2io)
    result.unsafeRun()
    println("Hi there .....")

//    println("Hello world ...")
//    println(wrapValue("Hello world").transform(s => SafeValue(s.toUpperCase)))
  }
}
