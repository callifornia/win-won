package spark

import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import spark.InitSparkSession._


object ReadData {

  def main(args: Array[String]): Unit = {
    readFromDB().show()
  }



  // JSON
  def readJson()(implicit spark: SparkSession): DataFrame = {
    lazy val carsSchema =
      StructType(Array(
        StructField("Name", StringType),
        StructField("Miles_per_Gallon", DoubleType),
        StructField("Cylinders", LongType),
        StructField("Displacement", DoubleType),
        StructField("Horsepower", LongType),
        StructField("Weight_in_lbs", LongType),
        StructField("Acceleration", DoubleType),
        StructField("Year", DateType),
        StructField("Origin", StringType)))

    spark
      .read
      .schema(carsSchema)
      .option("dateFormat", "yyyy-MM-d")
      .option("allowSingleQuotes", "true")
      .option("compression", "uncompressed")
      .json("src/main/resources/essential/cars.json")
  }

  def readJson2()(implicit spark: SparkSession): DataFrame =
    spark
      .read
      .option("recursiveFileLookup", "true")
      .option("inferSchema", "true")
      .json("s3://match-services-wrappers-results/CompanyMasterEnrich/ucc/2025-03-11-14-00/")
      .distinct()



  // CSV
  def readCSV()(implicit spark: SparkSession): DataFrame = {
    val stockSchema = StructType(
      Array(
        StructField("symbol", StringType),
        StructField("date", DateType),
        StructField("price", DoubleType)))

    spark.read
      .schema(stockSchema)
      .option("dateFormat", "yyyy-MM-d")
      .option("header", "true")
      .option("sep", ",")
      .option("nullValue", "")
      .csv("src/main/resources/essential/stocks.csv")
  }



  // Parquet
  def writeParquet()(implicit spark: SparkSession): Unit =
    readCSV()
      .write
      .mode(SaveMode.Overwrite)
      .save("src/main/resources/essential/parquet/stocks.parquet")



  // Text file
  def readTxtFile()(implicit spark: SparkSession): DataFrame =
    spark
      .read
      .text("src/main/resources/essential/someFile.txt")



  // Read from DB
  def readFromDB()(implicit spark: SparkSession): DataFrame =
    spark
      .read
      .format("jdbc")
      .option("driver", "org.postgresql.Driver")
      .option("url", "jdbc:postgresql://localhost:5432/test")
      .option("user", "postgres")
      .option("password", "admin")
      .option("dbtable", "records.company")
      .load()



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
}
