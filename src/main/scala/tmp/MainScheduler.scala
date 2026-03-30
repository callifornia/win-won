package tmp

object MainScheduler {

  def main(args: Array[String]): Unit = {

    val days            = Seq("понеділок", "вівторок", "середа", "четверг", "пятниця", "суббота", "неділя")
    val mandatoryPoints = Seq(("Conspect repeat", "Algorithm", "DataStructure",  "Відправка Резюме"))
    val topics = Seq(
        "Akka actor-typed",
        "Akka http",
        "Akka stream",
        "Cats",
        "ZIO",
        "Apache Spark",
        "SQL",
        "Знайти ресурси на котрих можна створювати AI відео реклами та іншого",
        "Scala programming",
        "Play Framework",
        "Slick",
        "AWS",
        "Docker",
        "Git",
        "Macos")

    val n = Math.ceil(topics.size.toDouble / days.size.toDouble).toInt


    // topics - 15
    // days   - 7

    val days_all = (1 to n).foldLeft(Seq.empty[String]) { (acc, el) =>
      acc ++ days
    }


      for {
        (day, topic)                                     <- days_all.zip(topics)
        (conspect, algorithm, dataStructure, sendResume) <- mandatoryPoints
      } yield println(
        s"""
           | $day
           |  - $conspect
           |  - $algorithm
           |  - $dataStructure
           |  - $topic
           |  - $sendResume
           |""".stripMargin)



//    println(
//      s"""
//         |n: $n
//         |days_all: ${days_all.take(topics.size)}
//         |days_all.size: ${days_all.size}
//         |days_all.size.___: ${days_all.take(topics.size).size}
//         |topics: ${topics.size}
//         |""".stripMargin)
  }
}
