package ______conspect______.big_data.spark


import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import spark.util.InitSession._
import util.Util.Spark._
import spark.implicits._


object Main {

  def main(args: Array[String]): Unit = {
//    jdbc_parquet()
//    write2Parquet()
//    readTheMovies()
//    readAndCountTheMovie()
    readCars()
  }



  def readCars(): Unit = {
    val data = readJson("src/main/resources/spark/daniel/data/cars.json")
    val regex = "volkswagen|vw"
    data.show()
//    val vwCarsDF =
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
