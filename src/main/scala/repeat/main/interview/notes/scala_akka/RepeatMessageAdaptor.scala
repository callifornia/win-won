package repeat.main.interview.notes.scala_akka

import akka.actor.ActorRef
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import Receiver.GetData

object RepeatMessageAdaptor {
  def main(args: Array[String]): Unit = {

//    val system = ActorSystem(rootBehavior(), "actor-system")
//    system ! "start"
  }

  object Customer {
    trait Request
  }
//  case class GetData(replyTo: ActorRef[Customer.Request]) extends Customer.Request
//
//  def rootBehavior(): Behavior[String] =
//    Behaviors.setup[String] { context =>
//      val shoppingCartActor = context.spawn(shoppingCartBehavior(), "shopping-cart-behavior")
//      val checkoutActor = context.spawn(checkoutBehavior(shoppingCartActor), "checkout-behavior")
//      val customerActor = context.spawn(customerBehavior(), "customer-actor")
//
//      Behaviors.receive {
//        (context, msg) =>
//          msg match {
//            case "start" =>
//              checkoutActor ! GetData(replyTo = customerActor)
//          }
//      }
//
//      Behaviors.same[String]
//    }


}


