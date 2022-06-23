package interview_repeat
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.Behavior
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import akka.actor.typed.ActorSystem
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern._

object Main {
  def main(args: Array[String]): Unit = {

  }
}

/*
*                       Type classes
*
*
* Type class is the thing which provide some interface for some kind of defined types
*
*
*
*
* String                 -> JsonValue
* Int                    -> JsonValue
* Map[String, JsonValue] -> JsonValue
*
* */
trait JsonValue {
  def stringify: String
}

case class IntJsonValue(value: Int) extends JsonValue {
  def stringify: String = value.toString
}

case class StringJsonValue(value: String) extends JsonValue {
  def stringify: String = "\"" + value + "\""
}

case class JsonObjectValue(value: Map[String, JsonValue]) extends JsonValue {
  def stringify: String =
    value.map {
      case (key, value) => "\"" + key + "\":" + "" + value.stringify + ""
    }.mkString("{", ",", "}")
}


/* case classes */
case class ScoredPoint(amount: Amount)
case class Amount(value: Int)
case class Description(value: String)


object toJsonWrapper {

  /* main interface */
  trait Json[T] {
    def toJson(value: T): JsonValue
  }

  implicit class JsonSyntax[T](value: T) {
    def toJson(implicit converter: Json[T]): JsonValue = converter.toJson(value)
  }

  implicit object IntJson extends Json[Int] {
    def toJson(value: Int): JsonValue = IntJsonValue(value)
  }

  implicit object StringJson extends Json[String] {
    def toJson(value: String): JsonValue = StringJsonValue(value)
  }

  implicit object DescriptionJson extends Json[Description] {
    def toJson(value: Description): JsonValue =
      JsonObjectValue(Map("description" -> StringJsonValue(value.value)))
  }

  implicit object AmountJson extends Json[Amount] {
    def toJson(value: Amount): JsonValue = JsonObjectValue(
      Map(
        "amount" -> JsonObjectValue(Map(
          "value" -> value.value.toJson))
      )
    )
  }

  implicit object ScoredPoint extends Json[ScoredPoint] {
    def toJson(value: ScoredPoint): JsonValue =
      JsonObjectValue(Map(
        "scoredPoint" -> value.amount.toJson)
      )
  }

  /* usage */

  Description("some description").toJson.stringify


  /*
  *
  *   Another example:  Via type classes we can add new functionality into the case classes
  *
  * */

  trait Pet[A] {
    def rename(a: A, newName: String): A
  }

  case class Fish(name: String)
  object Fish {
    implicit object RenameFish extends Pet[Fish] {
      def rename(a: Fish, newName: String): Fish = a.copy(newName)
    }
  }

  // sharing for user
  object Pet {
    implicit class PetSyntax[A](a: A)(implicit impl: Pet[A]) {
      def rename(newName: String): A = impl.rename(a, newName)
    }
  }
  import Pet._
  Fish("Boris").rename("Liza")




/*
*                               Monad
*
*  Extract -> Transform -> Wrap
*  1. Wrap a value: class `CustomMonad` wrap `value`. In functional world it's named as a `pure` or `unit`
*  2. Transform a value by the given function, in our case it's a `flatMap`: T => CustomMonad[S]
*
*
*  Laws:
*  Monad(x).flatMap(f) == f(x)                                                  left identity
*  Monad(x).flatMap(x => Monad(x)) == Monad(x)                                  right identity (USELESS)
*  Monad(x).flatMap(f).flatMap(g) == Monad(x).flatMap(x => f(x).flatMap(g))     composition, associativity
*
* */

  case class CustomMonad[+T](private val value: T) {
    def get(): T = value

    def flatMap[S](function: T => CustomMonad[S]): CustomMonad[S] =
      function(value)
  }



/*
*                               Functor
*
* A Functor for a type provides the ability for its values to be "mapped over",
* i.e. apply a function that transforms inside a value while remembering its shape.
* For example, to modify every element of a collection without dropping or adding elements.
*
*
* Laws:
*   fa:Functor[F[_]]
*   fa.map(x => x)   == fa                                identity
*   fa.map(f).map(g) == fa.map(x => f(x) andThen g(x))    composition
*
* */


  /* main trait */
  trait CustomFunctor[C[_]] {
    def map[A, B](container: C[A])(function: A => B): C[B]
  }

  /* Extension for everyone */
  object CustomFunctor {
    implicit class CustomFunctorSyntax[A, B, C[_]](container: C[A])(implicit functor: CustomFunctor[C]) {
      def map(function: A => B): C[B] = functor.map(container)(function)
    }
  }

  case class Foo[A](value: A)
  object Foo {
    /* concrete implementation */
    implicit object FooFunctorImpl extends CustomFunctor[Foo] {
      def map[A, B](container: Foo[A])(function: A => B): Foo[B] =
        Foo(function(container.value))
    }
  }

  /* usage */
  import CustomFunctor._
  Foo(123).map(_ + 2)



/*
*                           F-Bound polymorphism
*
* F-bounded polymorphism (a.k.a self-referential types, recursive type signatures, recursively bounded quantification)
* is a powerful object-oriented technique that leverages the type system to encode constraints on generics
*
* это мощный объектно-ориентированный метод, который использует систему типов для кодирования ограничений на дженерики.
* */

  trait Pets[A <: Pets[A]] { this: A =>
    def rename(str: String): A
  }

  case class Fish2(name: String) extends Pets[Fish2] {
    def rename(str: String): Fish2 = copy(str)
  }

  case class Dog(name: String) extends Pets[Dog] {
    def rename(str: String): Dog = copy(str)
  }

  class Mammut(name: String) extends Dog(name)



/*
*                             Higher-kinded types
*
* A higher-kinded type is a type that abstracts over some type that, in turn, abstracts over another type.
* It's a way to generically abstract over entities that take type constructors.
* They allow us to write modules that can work with a wide range of objects.
*
*
*                            Ad-Hoc Polymorphism (overload)
*
* One of the easiest ways to think about ad-hoc polymorphism is in terms of a switch statement that's happening
* in the background but without a default case. In this case, the compiler switches between different code
* implementations depending on the type of input a method receives.
*
*
*
*                               Associativity
* a x (b x c) = (a x b) x c
*
*
*
*                                   Kleisli
*
* We do have:
*   f: Int => String
*   g: String => Double
* So we can compose both functions in a way: f andThen g
*
*   h: String => Option[Int]
*   p: Int => Option[Double]
* We can not write: h andThen p, here is where Kleisli comes to play
*
*
*
* flatMap is not a hard requirement
* so basically we can implement with a map: but in this case Functor is required
*
* */

  case class Kleisli[F[_], In, Out](run: In => F[Out]) {
    import cats.FlatMap
    import cats.implicits._
    // Kleisli[F[_], In, Out] current
    def andThen[Out2](k: Kleisli[F, Out, Out2])(implicit f: FlatMap[F]): Kleisli[F, In, Out2] =
      Kleisli[F, In, Out2](in => run.apply(in).flatMap(out => k.run(out)))

    import cats.Functor
    // also we can do something like this where we put simple function: A => B
    def map[Out2](k: Out => Out2)(implicit f: Functor[F]): Kleisli[F, In, Out2] =
      Kleisli[F, In, Out2](in => run(in).map(out => k(out)))
  }



/*
*                                       Actor
*
* is a function:  Message => Behavior[Message]
*
*   Behavior[Message]
*     - can do side effect
*     - can hold a state
*     - can change the way of reacting on some messages
* */

  case class Hello(value: String)
  case class SayHello(name: String, replyTo: ActorRef[Hello])

  object FirstActor {
    def process(): Behavior[SayHello] =
      Behaviors.receiveMessage {
        case SayHello(name, replyTo) =>
          println("SayHello is going to reply ...")
          replyTo ! Hello(name + " hello from FirstActor")
          Behaviors.same
      }
  }

  def callAnActor(): Unit = {
    implicit val timeout = Timeout(3.seconds)
    implicit val sys: ActorSystem[SayHello] = ActorSystem(FirstActor.process(), "SomeActorSystem")
    implicit val ec = sys.executionContext

    sys
      .ask(replyTo => SayHello("Jeremmy", replyTo))
      .foreach(result => println("Result is: " + result))
  }

  /*
     pipeTo => handle Future from some service and trunsform it into the message which are going to be send to itself

     context.pipeToSelf(ExternalService.findPhone(name)) {
        case Success(phone) => PersonPhone(phone)
        case Failure(exception) => PhoneNotFound(name, exception)
    }

  */




}


