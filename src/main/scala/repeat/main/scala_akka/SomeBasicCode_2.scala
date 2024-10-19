package repeat.main.scala_akka

import akka.actor.typed.ActorRef


object SomeBasicCode_2 {
  def main(args: Array[String]): Unit = {
    println("Hi there ...")
  }
}

object StoreDomain{
  case class Product(name: String, price: Double)
}

object ShoppingCart {
  import StoreDomain._
  sealed trait Request
  case class GetCurrentCart(cartId: String, replyTo: ActorRef[Response]) extends Request

  sealed trait Response
  case class CurrentCart(cartId: String, items: List[Product]) extends Response

}

object Checkout {
  sealed trait Request
  final case class InspectSummary(cardId: String, replyTo: ActorRef[Response])
}













































