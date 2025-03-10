package spark

import org.apache.spark.sql.SparkSession

trait InitSparkSession {
  implicit val spark = SparkSession
    .builder()
    .appName("lessons")
    .master("local[*]")
    .getOrCreate()
}

object InitSparkSession extends InitSparkSession
