// loan pattern

case  class Session(str: String)

def builder(handle: => Session => Unit): Unit =
  handle(Session("Hi there"))




builder { session =>
  println("Builder method ... " + session.str)
}