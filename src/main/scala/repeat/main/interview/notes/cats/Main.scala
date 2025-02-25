package repeat.main.interview.notes.cats


object Main {

  def main(args: Array[String]): Unit = {

    case class Reader[A, B](run: A => B) {
      def apply(x: A): B = run(x)
      def map[C](f: B => C): Reader[A, C] = Reader[A, C](x => run.andThen(f).apply(x))
      def flatMap[C](f: B => Reader[A, C]): Reader[A, C] = Reader[A, C](x => map(f).apply(x).apply(x))
    }

    trait UserRepository {
      def create(x: String): Option[Boolean]         = Some(true)
      def update(x: String): Either[String, Boolean] = Right(true)
      def delete(x: String): Either[String, Int]     = Right(3)
    }

    val result =
      for {
        created <- Reader[UserRepository, Option[Boolean]](_.create("foo"))
        updated <- Reader[UserRepository, Either[String, Boolean]](_.update("bar"))
        deleted <- Reader[UserRepository, Either[String, Int]](_.delete("bar"))
      } yield (created, updated, deleted)

    result.apply(new UserRepository {})
  }
}
