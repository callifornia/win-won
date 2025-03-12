package spark.exercies

import org.apache.spark.sql.functions.{col, column, expr}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Column, DataFrame, SaveMode, SparkSession}
import spark.InitSparkSession._
import spark.implicits._



object Exercises {


  def main(args: Array[String]): Unit = {
    exercise_2()

  }


  /*
  * Exercises:
  *   - read the movies DF and select 2 columns of your choice
  *   - Create another column summing up the total profit of the: US_Gross, Worldwide_Gross, DVD_sells
  *   - Select all COMEDY movies with IMDB_Rating > 6
  *
  *   Use as many versions as possible
  * */
  def exercise_2()(implicit spark: SparkSession): Unit = {
    val movies = spark.read.option("inferSchema", "true").json("src/main/resources/essential/movies.json").distinct()
    readColumnsAllWays(movies)
    sumColumns(movies)
    commedyMovies(movies)
  }


  def commedyMovies(movies: DataFrame): Unit = {
    movies.filter("IMDB_Rating > 6")
    movies.where("IMDB_Rating > 6")
    movies.filter(col("IMDB_Rating") > 6)
    movies.filter(col("Major_Genre") === "Comedy" and col("IMDB_Rating") > 6).show()
    movies
      .where(col("Major_Genre") === "Comedy")
      .where(col("IMDB_Rating") > 6)
      .show()
  }


  def sumColumns(movies: DataFrame): Unit = {
    val sumColumn: Column  = movies.col("US_Gross") + movies.col("Worldwide_Gross") + movies.col("US_DVD_Sales")
    val sumColumn2: Column = expr("US_Gross + Worldwide_Gross + US_DVD_Sales")
    movies.withColumn("sum column", sumColumn)
    movies.withColumn("sum column", col("US_Gross") + col("Worldwide_Gross") + col("US_DVD_Sales")).show()

//    movies.withColumn("sum column", sumColumn2).show()
  }


  def readColumnsAllWays(movies: DataFrame): Unit = {
    movies.select("Title", "US_Gross").show()
    movies.select($"Title", $"US_Gross").show()
    movies.select(col("Title"), col("US_Gross")).show()
    movies.select(column("Title"), column("US_Gross")).show()
    movies.select('Title, 'US_Gross).show()
    movies.select(expr("Title"), expr("US_Gross")).show()
    movies.select(movies.col("Title"), movies.col("US_Gross")).show()
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
