package repeat.main.scala_akka

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import repeat.main.scala_akka.Checkout.{InspectSummary, Summary}
import StoreDomain._
import ShoppingCart._

import scala.util.Random



object SomeBasicCode_2 {
  def main(args: Array[String]): Unit = {
    println("Hi there ...")





  }

  def rootBehavior(): Behavior[Any] =
    Behaviors.setup[Any] { implicit context =>
      val customerActor = context.spawn(CustomerActor.handle(), "custom-actor")
      val shopingCartActor = context.spawn(ShoppingCartActor.handle(), "shopping-cart-actor")

      val checkoutActor = context.spawn(CheckoutActor.handle(
        shoppingCart = ShoppingCartActor(shopingCartActor),
        checkoutsInProgress = ???
      ))


      Behaviors.same[Any]
    }




}




object RequestResponseWrappers {
  case class WrappedStuff(response: ShoppingCart.Response) extends Checkout.Request

  def wrappedReplyTo(implicit context: ActorContext[Checkout.Request]): ActorRef[ShoppingCart.Response] =
    context.messageAdapter(rsp => WrappedStuff(rsp))
}


object CustomerActor {
  def handle(): Behavior[Any] =
    Behaviors.receive[Any] { (context, msg) =>
      msg match {
        case Checkout.Summary(cardId, amount) =>
          context.log.info(s"Total to pay: [${amount}] for cartId: [$cardId]")
          Behaviors.same[Any]
      }
    }
}

case class CustomerActor(ref: ActorRef[Checkout.Response])
case class ShoppingCartActor(ref: ActorRef[ShoppingCart.Request])


object CheckoutActor {
  import RequestResponseWrappers._

  def handle(shoppingCart: ShoppingCartActor,
             checkoutsInProgress: Map[CardId, CustomerActor] = Map.empty)
            (implicit context: ActorContext[Checkout.Request]): Behavior[Checkout.Request] = {
    ///
    Behaviors.receiveMessage[Checkout.Request] {
      /*
         Requests handling
         message from customer - query the shopping cart, the recipient of that response is my message adapter
      */
      case InspectSummary(cartId, replyTo) =>
        shoppingCart.ref ! ShoppingCart.GetCurrentCart(cartId, wrappedReplyTo)  /* <--- message adapter here */
        handle(shoppingCart, checkoutsInProgress + (cartId -> replyTo))


      /*
        Responses handling
        the wrapped message from my adapter: deal with the Shopping Cart's response here
      */
      case WrappedStuff(CurrentCart(cartId, items)) =>
        val summary = Summary(cartId, items.map(_.price).sum)
        val customerActor = checkoutsInProgress(cartId)
        customerActor.ref ! summary
        Behaviors.same
    }
  }
}


object ShoppingCartActor extends StaticMockedData {
  def handle(): Behavior[ShoppingCart.Request] =
    Behaviors.receiveMessage {
      case GetCurrentCart(cartId, replyTo) =>
        replyTo ! CurrentCart(cartId, mapa(cartId))
        Behaviors.same[ShoppingCart.Request]
    }
}








object StoreDomain{
  case class Product(name: String, price: Double)
  case class CardId(value: Int) extends AnyVal
}


object Customer {
  sealed trait Request

}


object ShoppingCart {
  sealed trait Request
  case class GetCurrentCart(cartId: CardId, ref: ActorRef[Response]) extends Request

  sealed trait Response
  case class CurrentCart(cartId: CardId, items: List[Product]) extends Response
}



object Checkout {
  sealed trait Request
  final case class InspectSummary(cardId: CardId, replyTo: ActorRef[Response]) extends Request

  sealed trait Response
  final case class Summary(cartId: CardId, amount: Double) extends Response
}







trait StaticMockedData {
  lazy val cartId1 = CardId(Random.nextInt(2))
  lazy val cartId2 = CardId(Random.nextInt(2))
  lazy val cartId3 = CardId(Random.nextInt(2))

  private def db(): List[Product] =
    Product(name = Random.nextString(3), price = Random.nextInt(2)) ::
      Product(name = Random.nextString(3), price = Random.nextInt(2)) ::
      Product(name = Random.nextString(3), price = Random.nextInt(2)) :: Nil

  protected lazy val mapa = Map(cartId1 -> db, cartId2 -> db, cartId3 -> db)
}












































