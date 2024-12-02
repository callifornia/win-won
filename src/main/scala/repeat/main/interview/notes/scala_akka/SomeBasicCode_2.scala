package repeat.main.interview.notes.scala_akka

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import Checkout.{InspectSummary, Summary}
import StoreDomain._
import ShoppingCart._

import scala.util.Random


object SomeBasicCode_2 {
  def main(args: Array[String]): Unit = {
    println("Hi there ...")
    val system = ActorSystem(rootBehavior(), "actor-system")
    system ! "start"
  }


  def rootBehavior(): Behavior[String] =
    Behaviors.setup[String] {implicit context =>
      val shoppingCartActor = context.spawn(ShoppingCartActor.handle(), "shopping-cart-actor")
      val checkoutActor     = context.spawn(CheckoutActor.handle(shoppingCart = ShoppingCartActor(shoppingCartActor)), "checkout-actor")
      val customerActor     = context.spawn(CustomerActor.handle(), "custom-actor")

      Behaviors.receiveMessage[String] {
        case "start" =>
          checkoutActor ! InspectSummary(StaticMockedData.cartId1, customerActor)
          Behaviors.same[String]
      }
    }
}




object CustomerActor {
  def handle(): Behavior[Checkout.Response] =
    Behaviors.receive[Checkout.Response] {(context, msg) =>
      msg match {
        case Checkout.Summary(cardId, amount) =>
          context.log.info(s"Total to pay: [${amount}] for cartId: [$cardId]")
          Behaviors.same[Checkout.Response]
      }
    }
}



case class CustomerActor(ref: ActorRef[Checkout.Response])
case class ShoppingCartActor(ref: ActorRef[ShoppingCart.Request])



object RequestResponseWrappers {
  case class WrappedStuff(response: ShoppingCart.Response) extends Checkout.Request

  def wrappedReplyTo(context: ActorContext[Checkout.Request]): ActorRef[ShoppingCart.Response] =
    context.messageAdapter(response => WrappedStuff(response))
}



object CheckoutActor {

  import RequestResponseWrappers._

  def handle(shoppingCart: ShoppingCartActor,
             checkoutsInProgress: Map[CardId, CustomerActor] = Map.empty): Behavior[Checkout.Request] =
    Behaviors.receive[Checkout.Request] {
      (context, msg) =>
        msg match {
          /*
             Requests handling
             message from customer - query the shopping cart, the recipient of that response is my message adapter
          */
          case InspectSummary(cartId, replyTo) =>
            context.log.info(s"Checkout actor InspectSummary: [$cartId]")
            shoppingCart.ref ! ShoppingCart.GetCurrentCart(cartId, wrappedReplyTo(context)) /* <--- message adapter here */
            handle(shoppingCart, checkoutsInProgress + (cartId -> CustomerActor(replyTo)))


          /*
            Responses handling
            the wrapped message from my adapter: deal with the Shopping Cart's response here
          */
          case WrappedStuff(CurrentCart(cartId, items)) =>
            context.log.info(s"Checkout actor WrappedStuff: [$cartId]")
            val summary = Summary(cartId, items.map(_.price).sum)
            val customerActor = checkoutsInProgress(cartId)
            customerActor.ref ! summary
            Behaviors.same
        }
    }
}


object ShoppingCartActor extends StaticMockedData {
  def handle(): Behavior[ShoppingCart.Request] =
    Behaviors.receive {
      (context, msg) =>
        msg match {
          case GetCurrentCart(cartId, replyTo) =>
            context.log.info("ShoppingCart actor GetCurrentCart")
            replyTo ! CurrentCart(cartId, mapa(cartId))
            Behaviors.same[ShoppingCart.Request]
        }
    }
}


object StoreDomain {
  case class Product(name: String, price: Double)
  case class CardId(value: Int) extends AnyVal
}



object ShoppingCart {
  sealed trait Request
  case class GetCurrentCart(cartId: CardId, ref: ActorRef[Response]) extends Request

  sealed trait Response
  case class CurrentCart(cartId: CardId, items: List[Product]) extends Response
}



object Checkout {
  sealed trait Request
  final case class InspectSummary(cardId: CardId, replyTo: ActorRef[Checkout.Response]) extends Request

  sealed trait Response
  final case class Summary(cartId: CardId, amount: Double) extends Response
}



trait StaticMockedData {
  lazy val cartId1 = CardId(1)
  lazy val cartId2 = CardId(2)
  lazy val cartId3 = CardId(3)

  private def db(): List[Product] =
    Product(name = Random.nextString(3), price = Random.nextInt(2)) ::
      Product(name = Random.nextString(3), price = Random.nextInt(2)) ::
      Product(name = Random.nextString(3), price = Random.nextInt(2)) :: Nil

  println(s"""
     | cartId1: $cartId1
     | cartId2: $cartId2
     | cartId3: $cartId3
     |""".stripMargin)
  protected lazy val mapa = Map(cartId1 -> db, cartId2 -> db, cartId3 -> db)
}

object StaticMockedData extends StaticMockedData












































