package sparkk

import org.apache.spark.sql.classic.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.StringType
import util.InitSession._

import java.util.regex.Pattern
//import util.Spark._
import spark.implicits._
import org.apache.spark.sql.functions._


object SparkExercises {
  case class MovieRatings(movieName: String, rating: Double)
  case class MovieCritics(name: String, movieRatings: Seq[MovieRatings])

  def main(args: Array[String]): Unit = {
    SparkWorkshop.exercise_6_1()
  }


  object SparkWorkshop {


    // Merging two rows
    def exercise_6_1(): Unit = {
      val df = Seq(
        ("100","John", Some(35),None),
        ("100","John", None,Some("Georgia")),
        ("101","Mike", Some(25),None),
        ("101","Mike", None,Some("New York")),
        ("103","Mary", Some(22),None),
        ("103","Mary", None,Some("Texas")),
        ("104","Smith", Some(25),None),
        ("105","Jake", None,Some("Florida"))).toDF("id", "name", "age", "city")

      df.show(truncate = false)




    }





    // structs for column names and values
    /* with explode and group by and pivot */
    def exercise_5_1(): Unit = {
      val data = Seq(
        MovieCritics("Manuel", Seq(MovieRatings("Logan", 1.5), MovieRatings("Zoolander", 3), MovieRatings("John Wick", 2.5))),
        MovieCritics("John", Seq(MovieRatings("Logan", 2), MovieRatings("Zoolander", 3.5), MovieRatings("John Wick", 3))))

      val df = data.toDF()
      df
        .withColumn("movieRatingExploded", explode($"movieRatings"))
        .withColumn("moviName", $"movieRatingExploded.movieName") //  .select($"name", $"exploded.*")
        .withColumn("rating", $"movieRatingExploded.rating")
        .groupBy("name")
        .pivot("moviName")
        .agg(first("rating"))
        .show()
    }

    /* without group by and pivot */
    def exercise_5_2(): Unit = {
      val data = Seq(
        MovieCritics("Manuel", Seq(MovieRatings("Logan", 1.5), MovieRatings("Zoolander", 3), MovieRatings("John Wick", 2.5))),
        MovieCritics("John", Seq(MovieRatings("Logan", 2), MovieRatings("Zoolander", 3.5), MovieRatings("John Wick", 3))))
      val df = data.toDF()
      /*
        +------+--------------------------------------------------+
        |name  |movieRatings                                      |
        +------+--------------------------------------------------+
        |Manuel|[{Logan, 1.5}, {Zoolander, 3.0}, {John Wick, 2.5}]|
        |John  |[{Logan, 2.0}, {Zoolander, 3.5}, {John Wick, 3.0}]|
        +------+--------------------------------------------------+
      * */

      val ratingSize: Int = df
        .withColumn("size", size($"movieRatings"))
        .select(max("size"))
        .as[Int]
        .head() // 3

      /*
        {Logan, 1.5}, {Zoolander, 3.0}, {John Wick, 2.5}
        size 3
      */

      // fetch values for each column by index (previous step)
      val all = (0 until ratingSize).foldLeft(df) {
        (ds, count) =>
          ds
            .withColumn(s"movie_name_$count", $"movieRatings"(count)("movieName"))
            .withColumn(s"rating_$count", $"movieRatings"(count)("rating"))
      }

      /*
      * +------+--------------------------------------------------+------------+--------+------------+--------+------------+--------+
        |name  |movieRatings                                      |movie_name_0|rating_0|movie_name_1|rating_1|movie_name_2|rating_2|
        +------+--------------------------------------------------+------------+--------+------------+--------+------------+--------+
        |Manuel|[{Logan, 1.5}, {Zoolander, 3.0}, {John Wick, 2.5}]|Logan       |1.5     |Zoolander   |3.0     |John Wick   |2.5     |
        |John  |[{Logan, 2.0}, {Zoolander, 3.5}, {John Wick, 3.0}]|Logan       |2.0     |Zoolander   |3.5     |John Wick   |3.0     |
        +------+--------------------------------------------------+------------+--------+------------+--------+------------+--------+
      * */

      all.show(truncate = false)
      val movieNameColumns = all.columns.filter(_.startsWith("movie_name")).map(col)
      val movieNameValues = all.select(movieNameColumns: _*).head().toSeq.map(_.toString)
      val ratingNameColumns = movieNameColumns.indices.map(i => s"rating_$i")

      /*
         movie column names are:    movie_name_0,movie_name_1,movie_name_2
         movie column values are:   Logan, Zoolander, John Wick
         ratingNameColumns:         rating_0,rating_1,rating_2
      * */

      /*  col(rating_0) as Logan      */
      /*  col(rating_1) as Zoolander  */
      /*  col(rating_2) as John Wick  */
      val movieWithRatingColumns = movieNameValues.zip(ratingNameColumns).map {
        case (movieName, ratingColumnName) => col(ratingColumnName) as movieName
      }

      val baseColumns = "name" :: "movieRatings" :: Nil map col
      val result = all.select((baseColumns ++ movieWithRatingColumns): _*)
      result.show(truncate = false)

    }


    /* solution */
    def exercise_5_3(): Unit = {

      val data = Seq(
        MovieCritics("Manuel", Seq(MovieRatings("Logan", 1.5), MovieRatings("Zoolander", 3), MovieRatings("John Wick", 2.5))),
        MovieCritics("John", Seq(MovieRatings("Logan", 2), MovieRatings("Zoolander", 3.5), MovieRatings("John Wick", 3))))

      val ratings = data.toDF()
      val ratingsCount =
        ratings
          .withColumn("size", size($"movieRatings"))
          .select(max("size"))
          .as[Int]
          .head

      val names_ratings =
        (0 until ratingsCount)
          .foldLeft(ratings) { case (ds, counter) =>
            ds
              .withColumn(s"name_$counter", $"movieRatings"(counter)("movieName"))
              .withColumn(s"rating_$counter", $"movieRatings"(counter)("rating"))
          }

      val movieColumns =
        names_ratings
          .columns
          .drop(1)
          .filter(name => name.startsWith("name"))
          .map(col)

      val movieNames = names_ratings.select(movieColumns: _*).head.toSeq.map(_.toString)

      val ratingNames = movieNames.indices.map(idx => s"rating_$idx")
      val cols = movieNames.zip(ratingNames).map { case (movie, rn) =>
        col(rn) as movie
      }
      names_ratings.select(($"name" +: cols): _*).show(truncate = false)
    }



    // limiting collect_set Standard Function
    def exercise_4(): Unit = {
      val df = spark.range(50).withColumn("key", $"id" % 5)

      df.show(100)
      df
        .groupBy("key")
        .agg(sort_array(collect_set("id")).as("all"))
        .withColumn("first three elements", slice($"all", 1, 3))
        .sort($"key")
        .show(truncate = false)
    }


    // adding count to the source DataFrame
    def exercise_3(): Unit = {
      val df = Seq(
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001", 2),
        ("05:49:56.604908", "10.0.0.3.5001", "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001", "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001", "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001", "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001", "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001", "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001", "10.0.0.2.54880", 2)).toDF("column0", "column1", "column2", "label")

      val windowFunction = Window.partitionBy("column1")

      df
        .withColumn("count", count("column1").over(windowFunction))
        .show()
    }


    // selecting the most important rows per assigned priority ...
    def exercises_2(): Unit = {
      val df = Seq(
        (1, "MV1"),
        (1, "MV2"),
        (2, "VPV"),
        (2, "Others")).toDF("id", "value")

      val windowFunction = Window.partitionBy("id").orderBy("value").rowsBetween(Window.unboundedPreceding, Window.currentRow)

      df
        .withColumn("row_number", row_number().over(windowFunction))
        .filter($"row_number" === 1)
        .show()
    }


    //  split function with variable delimiter per row ...
    def exercises_1(): Unit = {

      val dept = Seq(
        ("50000.0#0#0#", "#"),
        ("0@1000.0@", "@"),
        ("1$", "$"),
        ("1000.00^Test_string", "^"),
        ("", "^"),
        ("a^", "^")
      ).toDF("VALUES", "Delimiter")

      val quoteRegex = udf { (str: String) => Pattern.quote(str) }

      // via UDF
      dept
        .withColumn("split_values", split($"VALUES", quoteRegex($"Delimiter")))
        .withColumn("extra", filter($"split_values", _ =!= ""))

      // other ...
      dept
        .withColumn("split_values", array_remove(split($"VALUES", concat(lit("\\").cast(StringType), $"Delimiter")), ""))
        .show(truncate = false)


      // tutorial solution
      dept
        .withColumn("split_values", expr("""split(values, concat("\\", delimiter))"""))
        .withColumn("extra", array_remove('split_values, "")).show(truncate = false)
    }
  }
}








