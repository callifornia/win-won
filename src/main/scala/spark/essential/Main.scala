package spark.essential

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}


object Main {


  def main(args: Array[String]): Unit = {
    implicit val spark = SparkSession
      .builder()
      .appName("lessons")
      .master("local[*]")
      .getOrCreate()

//    readDataWithFailedJson()
    write()
  }




  def write()(implicit spark: SparkSession): Unit =
    readData()
      .write
      .format("json")
      .mode(SaveMode.Overwrite)
      .save("src/main/resources/essential/writes")



  /*
     ERROR Executor: Exception in task 0.0 in stage 0.0 (TID 0)
        org.apache.spark.SparkException: [MALFORMED_RECORD_IN_PARSING.WITHOUT_SUGGESTION]
        Malformed records are detected in record parsing: ...
        Parse Mode: FAILFAST.
  * */
  def readDataWithFailedJson()(implicit spark: SparkSession): Unit = {
    val moviewSchema = StructType(
      Array(
        StructField("Title", StringType),
        StructField("US_Gross", IntegerType),
        StructField("Worldwide_Gross", StringType),
        StructField("US_DVD_Sales", StringType),
        StructField("Production_Budget", StringType),
        StructField("Release_Date", StringType),
        StructField("MPAA_Rating", StringType),
        StructField("Running_Time_min", StringType),
        StructField("Distributor", StringType),
        StructField("Source", StringType),
        StructField("Major_Genre", StringType),
        StructField("Creative_Type", StringType),
        StructField("Director", StringType),
        StructField("Rotten_Tomatoes_Rating", StringType),
        StructField("IMDB_Votes", StringType)))

    spark
      .read
      .format("json")
      .schema(moviewSchema)
      .option("mode", "failFast") // with throw an Exception ...
      .option("path", "src/main/resources/essential/movies_invalid.json")
      .load
      .show()
  }


  def readDataWithFailedJsonAlternative()(implicit spark: SparkSession): Unit = {
    val moviewSchema = StructType(
      Array(
        StructField("Title", StringType),
        StructField("US_Gross", IntegerType),
        StructField("Worldwide_Gross", StringType),
        StructField("US_DVD_Sales", StringType),
        StructField("Production_Budget", StringType),
        StructField("Release_Date", StringType),
        StructField("MPAA_Rating", StringType),
        StructField("Running_Time_min", StringType),
        StructField("Distributor", StringType),
        StructField("Source", StringType),
        StructField("Major_Genre", StringType),
        StructField("Creative_Type", StringType),
        StructField("Director", StringType),
        StructField("Rotten_Tomatoes_Rating", StringType),
        StructField("IMDB_Votes", StringType)))

    spark
      .read
      .format("json")
      .schema(moviewSchema)
      .options(Map(
        "mode" -> "failFast",
        "path" -> "src/main/resources/essential/movies_invalid.json"))
      .load
      .show()
  }



  def readData()(implicit spark: SparkSession): DataFrame = {
    val moviesSchema = StructType(
      Array(
        StructField("Title", StringType),
        StructField("US_Gross", StringType),
        StructField("Worldwide_Gross", StringType),
        StructField("US_DVD_Sales", StringType),
        StructField("Production_Budget", StringType),
        StructField("Release_Date", StringType),
        StructField("MPAA_Rating", StringType),
        StructField("Running_Time_min", StringType),
        StructField("Distributor", StringType),
        StructField("Source", StringType),
        StructField("Major_Genre", StringType),
        StructField("Creative_Type", StringType),
        StructField("Director", StringType),
        StructField("Rotten_Tomatoes_Rating", StringType),
        StructField("IMDB_Votes", StringType)))

    spark
      .read
      .format("json")
      .schema(moviesSchema)
      .option("path", "src/main/resources/essential/movies.json")
      .load
  }





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
