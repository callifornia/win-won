package repeat.main.interview.notes.scala_akka

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import Receiver.{Data, GetData}
import Sender.RequestData

import scala.util.Random


object MessageAdaptor {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem(SenderActor.handle(), "sender-actor")
    system ! RequestData
    system ! RequestData
    system ! RequestData
    system ! RequestData

    Thread.sleep(10000)
  }
}



object WrapperContainer {
  case class Wrapper(response: Receiver.Response) extends Sender.Request
  def convertMessage(context: ActorContext[Sender.Request]): ActorRef[Receiver.Response] =
    context.messageAdapter(response => Wrapper(response))
}



object SenderActor {
  import WrapperContainer._
  def handle(): Behavior[Sender.Request] =
    Behaviors.receive {
      (context, msg) =>
        val receiver = context.spawn(ReceiverActor.handle(), Random.nextLong().toString)
        msg match {
          case RequestData =>
            context.log.info("Sender actor RequestData")
            receiver ! GetData(convertMessage(context))
            Behaviors.same
          case Wrapper(data: Data) =>
            context.log.info(s"Sender actor Wrapper(data: Data): $data")
            Behaviors.same
        }
    }
}



object ReceiverActor {
  def handle(): Behavior[Receiver.Request] =
    Behaviors.receive {
      (context, msg) =>
        msg match {
          case GetData(replyTo) =>
            context.log.info("Receiver actor GetData")
            replyTo ! Data(Random.nextInt(3))
            Behaviors.same[Receiver.Request]
        }
    }
}



object Sender {
  trait Request
  case object RequestData extends Request
}



object Receiver {
  trait Request
  case class GetData(replyTo: ActorRef[Response]) extends Request

  trait Response
  case class Data(value: Int) extends Response
}


