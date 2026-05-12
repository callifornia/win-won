package ______conspect______.big_data.spark


import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import spark.util.InitSession._
import util.Util.Spark._
import spark.implicits._
import util.Util.UdfFunctions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import scala.util.Try


object Main {

  def main(args: Array[String]): Unit = {
    //    jdbc_parquet()
    //    write2Parquet()
    //    readTheMovies()
    //    readAndCountTheMovie()
    //    readCars()
    movies()
  }


  def movies(): Unit = {

    val data = readJson("src/main/resources/spark/daniel/data/movies.json")
    val titleWithReleaseDate = data
      .select(
        $"Title",
        UdfFunctions.parseDate($"Release_Date").as("Actual_Release_Date"))

    titleWithReleaseDate.show(100, truncate = false)


    //    val movieDateDF = moviesWithReleaseDate
    //      .select(
    //        $"Title",
    //        $"Actual_Release")
    //      .withColumn("Today", current_date())
    //      .withColumn("Movie_Age", datediff($"Today", $"Actual_Release"))
    //      .withColumn("Right_now", current_timestamp())
    //
    //    movieDateDF.show(100, truncate = false)
  }


  def readCars(): Unit = {
    import spark.implicits._
    val data = readJson("src/main/resources/spark/daniel/data/cars.json")
    val regex = "volkswagen|vw"
    val isNotEmpty: String => Column = str => col(s"$str") =!= ""

    val resultDF = data
      .select(
        $"Name",
        regexp_extract($"Name", regex, 0).as("regex_extract")
      )
      .where(isNotEmpty("regex_extract"))
      .distinct()


    val resultList = resultDF.collect().toList
    println(resultList.mkString("\n"))
  }

  def readAndCountTheMovie(): Unit = {
    val data = readJson("src/main/resources/spark/daniel/data/movies.json")
    data
      .groupBy("Major_Genre")
      .agg(
        count("*").as("count_of_moview"),
        avg("IMDB_Rating").as("Rating_avg"))
      .show(100, truncate = false)
  }


  def readTheMovies(): Unit = {
    val data = readJson("src/main/resources/spark/daniel/data/movies.json")
    data
      .select("US_Gross", "Worldwide_Gross", "IMDB_Votes")
      .withColumn("Total", $"US_Gross" + $"Worldwide_Gross" + $"IMDB_Votes")
      .show(100, truncate = false)
  }


  def jdbc_parquet(): Unit = {
    // JDBC -> write 2 parquet
    val data = readFromDB("ua", "cat")
    data.show(100, truncate = false)
    data.write.mode("overwrite").save("src/main/resources/spark/exercies/")
    val check_data = spark.read.parquet("src/main/resources/spark/exercies/")
    check_data.show()
  }


  def write2Parquet(): Unit = {
    val data = spark.read.parquet("src/main/resources/spark/exercies/")
    write2DB(data, "ua", "cat_2")
  }
}
