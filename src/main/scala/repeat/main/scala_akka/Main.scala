package repeat.main.scala_akka

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world ... here is will be akka ...")

  }



  // akka
  {
    /*
      concurrency     - might not be executing simultaneously (паралельно)
      parallelism     - can be truly simultaneous (паралельно)
      synchronous     - caller cannot make progress until the method returns a value or throws an exception
      blocking        - if the delay of one thread can indefinitely delay some of the other threads
      asynchronous    - caller can make progress. Completion  may be signalled via some additional mechanism
                        Actors - are asynchronous by nature: an actor can progress after a message send without
                        waiting for the actual delivery to happen

      non-blocking    - means that no thread is able to indefinitely delay others
      deadlock        - arises when several participants are waiting on each other to reach a specific state to be
                        able to progress
      Race conditions - часто виникають, коли кілька потоків мають спільний змінний стан, і операції потоку над
                        станом можуть чергуватися, спричиняючи неочікувану поведінку
                        One example could be a client sending unordered packets (e.g. UDP datagrams) P1, P2 to a server.
                        As the packets might potentially travel via different network routes, it is possible that the
                        server receives P2 first and P1 afterwards. If the messages contain no information about their
                        sending order it is impossible to determine by the server that they were sent in a different order.
                        Depending on the meaning of the packets this can cause race conditions


      Actors are objects which encapsulate state and behavior, they communicate exclusively by exchanging messages which
     are placed into the recipient’s mailbox. In a sense, actors are the most stringent form of object-oriented programming,
     but it serves better to view them as persons: while modeling a solution with actors, envision a group of people and
     assign sub-tasks to them, arrange their functions into an organizational structure and think about how to escalate failure


      Error Kernel Pattern -  if one actor carries very important data (i.e. its state shall not be lost if avoidable),
                             this actor should source out any possibly dangerous sub-tasks to children and handle
                             failures of these children as appropriate. Depending on the nature of the requests,
                             it may be best to create a new child for each request, which simplifies state management
                             for collecting the replies
                              If one actor has multiple responsibilities each responsibility can often be pushed into a
                             separate child to make the logic and state more simple


     mutable objects between actors - Do not pass. In order to ensure that, prefer immutable messages. If the
                                      encapsulation of actors is broken by exposing their mutable state to the outside,
                                      you are back in normal Java concurrency land with all the drawbacks




     Actor can do the following three fundamental actions:
          - create a finite number of new Actors
          - send a finite number of messages to Actors it knows
          - designate the behavior to be applied to the next message



    An actor is a container for:
          - State
          - Behavior
          - Mailbox
          - Child Actors
          - Supervisor Strategy


    State
        - actors have an explicit lifecycle, they are not automatically destroyed when no longer referenced;
          it is your responsibility to make sure that it will eventually be terminated

        - restarting an actor without needing to update references elsewhere

        - actor references are parameterized and only messages that are of the specified type can be sent to them

        - akka actors conceptually each have their own light-weight thread, which is completely shielded from the rest of the system.
          This means that instead of having to synchronize access using locks you can write your actor code without worrying
          about concurrency at all

        - akka will run sets of actors on sets of real threads, where typically many actors share one thread, and subsequent
          invocations of one actor may end up being processed on different threads


    Behavior

        - behavior means a function which defines the actions to be taken in reaction to the message at that point in time
          This behavior may change over time. When behavior is changed under the hood new object is created which means
          StackOverflowError is not basically possible. So the code bellow:

                              def someMethod(): Behavior[String] =
                                Behavior.received {
                                  case _: String => someMethod()
                                }

        - actor references are parameterized and only messages that are of the specified type can be sent to them
          The association between an actor reference and its type parameter must be made when the actor reference (and its Actor) is created.
          For this purpose each behavior is also parameterized with the type of messages it is able to process


    Mailbox
      mail-box - the piece which connects sender and receiver is the actor’s. Each actor has one mailbox to which all
                 senders enqueue their messages. Default FIFO


    Child Actors
      Each actor is potentially a parent: if it creates children for delegating sub-tasks, it will automatically supervise them.
      The list of children is maintained within the actor’s context and the actor has access to it.
      Modifications to the list are done by spawning or stopping children and these actions are reflected immediately.
      The actual creation and termination actions happen behind the scenes in an asynchronous way, so they do not “block” their parent.


    Supervisor Strategy
      The final piece of an actor is its strategy for handling unexpected exceptions - failures. Fault handling is then
      done transparently by Akka, applying one of the strategies described in Fault Tolerance for each failure


    When an Actor Terminates
      Once an actor terminates, fails (in a way which is not handled by a restart), stops itself or is stopped by its supervisor,
      it will free up its resources, видаляє всі  remaining messages from its mailbox into the system’s “dead letter mailbox”
      which will forward them to the EventStream as DeadLetters.
      The mailbox is then replaced within the actor reference with a system mailbox, redirecting all new messages to the EventStream as DeadLetters
      This is done on a best effort basis, though, so do not rely on it in order to construct “guaranteed delivery”


    Supervision and Monitoring
      supervision provides the following three strategies:
          - restore the actor, keeping its accumulated internal state
          - restart the actor, clearing out its accumulated internal state, with a potential delay starting again
          - stop the actor permanently
        if all children of an actor has stopped unexpectedly it may make sense for the actor itself to restart or stop
      to get back to a functional state. This can be achieved through a combination of supervision and watching the
      children to get notified when they terminate. An example of this can be found in Bubble failures up through the hierarchy


      Top-Level actors
        /user:    the user guardian actor. When the user guardian stops the entire actor system is shut down
        /system:  the system guardian actor

      Lifecycle Monitoring
        -  ActorContext.watch(targetActorRef)     - start listening for Terminated messages
        -  ActorContext.unwatch(targetActorRef)   - stop listening, invoke
        -  Terminated message                     - to be received by the monitoring actor

      Actors and exceptions
        - if an exception is thrown while a message is being processed (i.e. taken out of its mailbox and handed over to the current behavior),
          then this message will be lost
        - Message is not put back on the mailbox. So if you want to retry processing of a message, you need to deal with it yourself by catching
          the exception and retry your flow


      What happens to the mailbox
        - If an exception is thrown while a message is being processed, nothing happens to the mailbox. If the actor is restarted,
          the same mailbox will be there. So all messages on that mailbox will be there as well


      What happens to the actor
        - If code within an actor throws an exception, that actor is:suspended and the supervision process is started.
          Depending on the supervisor’s decision the actor:
              - resumed (as if nothing happened),
              - restarted (wiping out its internal state and starting from scratch)
              - terminated


      Actor References, Paths and Addresses
        - ActorContext.self -> local reference to the actor itself
        - actor path        ->  an actor path consists of an anchor, which identifies the actor system,
                                followed by the concatenation of the path elements, from root guardian to the designated actor;
                                the path elements are the names of the traversed actors and are separated by slashes


      What is the Difference Between Actor Reference and Path?
        - actor reference - позначає a single actor and the life-cycle of the reference matches that actor’s life-cycle
        - actor path      - represents a name which може або не може бути населений актором and the path itself does not have a life-cycle,
                            it never becomes invalid
                          - you can create an actor path without creating an actor
                          - you cannot create an actor reference without creating a corresponding actor
                          - you can create an actor, terminate it, and then create a new actor with the same actor path
        - actor reference - to the old incarnation is not valid for the new incarnation
        - messages sent   - to the old actor reference will not be delivered to the new incarnation even though they have the same path


      Semantics of a delivery mechanism
        at-most-once    ->  message is delivered once or not at all; in more casual terms it means that messages may be lost
                            fire-and-forget fashion. is the cheapest
        at-least-once   ->  messages may be duplicated but not lost
        exactly-once    ->  message не може бути втрачена чи продубльована. is most expensive


      How do I Receive Dead Letters?
        - actor can subscribe to class akka.actor.DeadLetter on the event stream










     */

  }
}
