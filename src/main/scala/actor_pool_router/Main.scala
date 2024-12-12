package actor_pool_router

import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.Routers
import akka.actor.typed.SupervisorStrategy

object Worker {
  trait WorkerCommands

  case class LogMessage(msg: String) extends WorkerCommands

  def apply(): Behavior[WorkerCommands] = {
    Behaviors.receive {(context, message) =>
      message match {
        case LogMessage(msg) =>
          println(s"Worker [${context.self}] received the msg: $msg")
          apply()
        case a: Any =>
          println(s"Got any msg")
          apply()
      }
    }
  }

  object WorkerManager {
    trait WorkerManagerCommand

    case class DoLogMassages(msg: String) extends WorkerManagerCommand

    def apply(): Behavior[WorkerManagerCommand] = {
      Behaviors.setup[WorkerManagerCommand] {context =>
        val pool = Routers.pool(1)(Behaviors.supervise(Worker.apply()).onFailure[Exception](SupervisorStrategy.resume))
        val router = context.spawn(pool, "worker-pool")
        Behaviors.receiveMessage {
          case DoLogMassages(msg) =>
            1 to 5 foreach {counter =>
              println("WorkerManager going to send messages")
              router ! Worker.LogMessage(counter + "_" + msg)
            }

            //          val poolWithBroadCast = pool.pool
            apply()
        }
      }
    }
  }

  object Main {
    def run(): Unit = {
      val workerManager = ActorSystem(WorkerManager.apply(), "worker-manager")
      println("Going to ssend messages")
      workerManager ! WorkerManager.DoLogMassages("Akka is awesome")
      Thread.sleep(2000)
    }
  }
}

