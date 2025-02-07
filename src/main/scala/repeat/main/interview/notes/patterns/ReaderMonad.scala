package repeat.main.interview.notes.patterns

import cats.data.ReaderT

import java.util.concurrent.ConcurrentHashMap
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object ReaderMonad {
  def main(args: Array[String]): Unit = {
    val result = new Container
    println(result.eating())
  }
}


class Container {

  class KVStorage[K, V] {
    private val storage = new ConcurrentHashMap[K, V]

    def create(k: K, v: V): Future[Boolean] = Future.successful(storage.putIfAbsent(k, v) == null)
    def read(k: K): Future[Option[V]] = Future.successful(Option.apply(storage.get(k)))
    def update(k: K, v: V): Future[Unit] = Future.successful(storage.putIfAbsent(k, v))
    def delete(k: K): Future[Boolean] = Future.successful(storage.remove(k) != null)
  }


  type FoodName = String
  type Quantity = Int
  type FoodKV = KVStorage[FoodName, Quantity]


  class Fridge(iot: IoT) {

    def addFood(f: FoodName, q: Quantity): ReaderT[Future, FoodKV, Unit] = ReaderT { fs =>
      for {
        current <- fs.read(f)
        updated = current.map(_ + q)
        _       <- fs.update(f, updated.getOrElse(q))
        _       <- iot.notifyUser(s"$q of $f was added to Fridge")
      } yield updated
    }

    def removeFood(f: FoodName, q: Quantity): ReaderT[Future, FoodKV, Quantity] = ReaderT { fs =>
      for {
        current <- fs.read(f)
        inStock = current.getOrElse(0)
        taken   = Math.min(inStock, q)
        left    = inStock - taken
        _       <- if (left > 0) fs.update(f, left) else fs.delete(f)
        _       <- iot.notifyUser(s"$q of $f was removed from Fridge")
      } yield taken
    }
  }


  class Cooker(fr: Fridge) {
    def cookSouce(q: Quantity): ReaderT[Future, FoodKV, Quantity] =
      for {
        tomatoQ <- fr.removeFood("tomatto", q)
        vegQ <- fr.removeFood("non tomato veggies", q)
        _ <- fr.addFood("garlic", q * 2)
        sauceQ = tomatoQ / 2 + vegQ * 3 / 4
        _ <- fr.addFood("sauce", sauceQ)
      } yield q


    def cookPasta(q: Quantity): ReaderT[Future, FoodKV, Quantity] =
      for {
        pastaQ <- fr.removeFood("pasta", q)
        _ <- fr.removeFood("salt", 10)
        _ <- fr.addFood("cooked pasta", pastaQ)
      } yield pastaQ
  }

  trait IoT {
    def notifyUser(str: String): Future[Unit]
  }

  class GatewayIoT extends IoT {
    override def notifyUser(str: FoodName): Future[Unit] = Future.successful(println("Note: " + str))
  }

  lazy val fc = new FoodKV
  lazy val f = new Fridge(iot)
  lazy val c = new Cooker(f)
  lazy val iot = new GatewayIoT


  val shopping =
    for {
      _ <- f.addFood("tomato", 10)
      _ <- f.addFood("non-tomato", 15)
      _ <- f.addFood("garlic", 42)
      _ <- f.addFood("salt", 1)
      _ <- f.addFood("pasta", 5)
    } yield ()


  val cooking =
    for {
      _ <- shopping
      sq <- c.cookSouce(5)
      sp <- c.cookPasta(10)
    } yield s"Cooked $sq sauce and $sp pasta"

  def eating(): String = Await.result(cooking.run(fc), 2.seconds)

}
