package spark.projects.getting_started

import org.apache.spark.sql.DataFrame
import spark.InitSparkSession._

object Main {

  def main(args: Array[String]): Unit = {
    println("Hello world ...")
    val dataSet: DataFrame = spark.read.csv("src/main/resources/airtravel.csv")
    dataSet.show()
  }
}
