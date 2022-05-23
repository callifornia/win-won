package actor_receptionist
import akka.actor.typed.ActorSystem
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.Behavior
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.receptionist.ServiceKey
//import actor_receptionist.Worker.WorkerCommands


//object Worker {
//  val ServiceKey: ServiceKey[WorkerCommands] = ServiceKey[WorkerCommands]("worker")
//
//  def apply(): Behavior[WorkerCommands] =
//    Behaviors.setup[WorkerCommands] { context =>
//      // register itself just to be able to be discovered
//      context.system.receptionist ! Receptionist.Register(ServiceKey, context.self)
//
//      Behaviors.receiveMessage {
//        case WashDish(name) =>
//          println(s"Worker...going to wash dish ...")
//          Behaviors.same
//        case CleanRoom(name) =>
//          println(s"Worker...going to clean room ...")
//          Behaviors.same
//      }
//    }
//
//  sealed trait WorkerCommands
//  case class WashDish(name: String) extends WorkerCommands
//  case class CleanRoom(name: String) extends WorkerCommands
//}
//
//
//object WorkerService {
//
//  def apply(worker: ActorRef[WorkerCommands]): Behavior[WorkerServiceCommands] =
//    Behaviors.setup[WorkerServiceCommands] {
//      context =>
//
//
//    }
//
//  sealed trait WorkerServiceCommands
//  case object CleanKitchen extends WorkerServiceCommands
//}


object Main {
  @main def run(): Unit = {

  }
}
