package spark.essential
import org.apache.spark.sql.{DataFrame, SparkSession}


object Main {

  def main(args: Array[String]): Unit = {
//    readDataWithFailedJson()
  }



//
//  def write()(implicit spark: SparkSession): Unit =
//    readData()
//      .write
//      .format("json")
//      .mode(SaveMode.Overwrite)
//      .save("src/main/resources/essential/writes")


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
