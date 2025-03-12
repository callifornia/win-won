package spark.exercies

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import spark.InitSparkSession._



object Exercises {


  def main(args: Array[String]): Unit = {
    val movies = readMovies()
    writeCsv(movies)
    writeParquet(movies)
    writeToDB(movies)
  }



  /*
  * Exercises:
  *   - read the movies DF, then write it as:
  *     - csv     -> tab-separated values file
  *     - parquet -> snappy Parquet
  *     - db      -> table "public.movies" in the Postgres DB
  * */
  def exercises_1()(implicit spark: SparkSession): Unit = {
    val movies = readMovies()
    writeCsv(movies)
    writeParquet(movies)
    writeToDB(movies)
  }



  def readMovies()(implicit spark: SparkSession): DataFrame = {
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
      .schema(moviewSchema)
      .option("mode", "failFast") // with throw an Exception ...
      .json("src/main/resources/essential/movies.json")

  }



  def writeToDB(df: DataFrame)(implicit spark: SparkSession): Unit =
    df
      .write
      .format("jdbc")
      .mode(SaveMode.Overwrite)
      .option("driver", "org.postgresql.Driver")
      .option("url", "jdbc:postgresql://localhost:5432/test")
      .option("user", "postgres")
      .option("password", "admin")
      .option("dbtable", "records.movies")
      .save()



  def writeParquet(df: DataFrame)(implicit spark: SparkSession): Unit =
    df
      .write
      .mode(SaveMode.Overwrite)
      .parquet("src/main/resources/essential/exercises/movies/parquet")

  // the same as ".parquet("src/...." because by default DataFrame is saved as parquet file
  //      .save("src/main/resources/essential/exercises/movies/parquet")



  def writeCsv(df: DataFrame)(implicit spark: SparkSession): Unit =
    df
      .write
      .mode(SaveMode.Overwrite)
      .option("delimiter", "\t")
      .option("header", "true")
      .csv("src/main/resources/essential/exercises/movies/csv")

}
