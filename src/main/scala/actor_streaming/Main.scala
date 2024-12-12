package actor_streaming

import akka.NotUsed

object Main {
  def main(args: Array[String]): Unit = {
    // https://www.coursera.org/learn/scala-akka-reactive/lecture/j5IKf/lecture-6-1-stream-processing
    // Rock the jvm graph: https://www.youtube.com/watch?v=8XLB28KDtgg
    // https://doc.akka.io/docs/akka/current/stream/operators/Source-or-Flow/statefulMapConcat.html

//   val flow: Flow[Int, Int, NotUsed] = Flow.apply[Int].statefulMapConcat()
  }
}
