package actor_typed
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior

object Main {

  trait SomeMessages
  case object MessageA extends SomeMessages
  case object MessageB extends SomeMessages
  case object MessageC extends SomeMessages

  // This way should be avoided as a we do have a `var` as a state
  val mutableState: Behavior[SomeMessages] = {
    Behaviors.setup[SomeMessages] { context =>
      var counter = 0
      Behaviors.receiveMessage {
        case MessageA =>
          println(s"!!!!! MessageA: {$counter}")
          counter = counter + 1
          Behaviors.same[SomeMessages]
        case MessageB =>
          println(s"!!!!! MessageB: {$counter}")
          counter = counter + 2
          Behaviors.same[SomeMessages]
        case MessageC =>
          println(s"!!!!! MessageC: {$counter}")
          counter = counter + 3
          Behaviors.same[SomeMessages]
      }
    }
  }

  // This way is preferable as it has no `var` but instead we do call method with an updated `counter`
  def immutableStateActor(counter: Int = 0): Behavior[SomeMessages] =
    Behaviors.receiveMessage[SomeMessages] {
      case MessageA =>
        println(s"!!!!! MessageA: {$counter}")
        immutableStateActor(counter + 1)
      case MessageB =>
        println(s"!!!!! MessageB: {$counter}")
        immutableStateActor(counter + 2)
      case MessageC =>
        println(s"!!!!! MessageC: {$counter}")
        immutableStateActor(counter + 3)
    }


  @main def run(): Unit = {
    val mutableStateActor = ActorSystem(immutableStateActor(), "sad")
    mutableStateActor ! MessageA
    mutableStateActor ! MessageB
    mutableStateActor ! MessageC
    mutableStateActor ! MessageA
    mutableStateActor ! MessageA
    Thread.sleep(2000)
    mutableStateActor.terminate()
  }
}
