package spark.essential

import org.apache.spark.sql.{DataFrame, SparkSession}

object Main {


  def main(args: Array[String]): Unit = {
    implicit val spark = SparkSession
      .builder()
      .appName("lessons")
      .master("local[*]")
      .getOrCreate()

    exercise_2()
  }


  def readData()(implicit spark: SparkSession) =
    spark
      .read
      .format("json")
//      .schema()




  def exercise_1()(implicit spark: SparkSession): Unit = {
    import spark.implicits._
    val smartPhones = Seq(
      ("iphone", "s3", 123),
      ("samsung", "calaxy 21", 222),
      ("hitachi", "v1", 333),
      ("alcatel", "moon", 444))

    val phonesDF = smartPhones.toDF("phone-name", "model-name", "price")
    phonesDF.printSchema()
  }


  def exercise_2()(implicit spark: SparkSession): Unit = {
    val moviesDF: DataFrame = spark
      .read
      .format("json")
      .load("src/main/resources/essential/movies.json")

    moviesDF.printSchema()
    println(moviesDF.count())
  }

}
