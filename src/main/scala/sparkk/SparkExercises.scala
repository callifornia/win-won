package sparkk

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.StringType
import util.InitSession._

import java.util.regex.Pattern
//import util.Spark._
import spark.implicits._
import org.apache.spark.sql.functions._


object SparkExercises {

  def main(args: Array[String]): Unit = {
    SparkWorkshop.exercise_4()
  }


  object SparkWorkshop {

    // limiting collect_set Standard Function
    def exercise_4(): Unit = {
      val df = spark.range(50).withColumn("key", $"id" % 5)

      df.show(100)
      df
        .groupBy("key")
        .agg(collect_list("id").as("all"))
        .sort($"key")
        .show(truncate = false)
    }

    // adding count to the source DataFrame
    def exercise_3(): Unit = {
      val df = Seq(
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604900", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604899", "10.0.0.2.54880", "10.0.0.3.5001",  2),
        ("05:49:56.604908", "10.0.0.3.5001",  "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001",  "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001",  "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001",  "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001",  "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001",  "10.0.0.2.54880", 2),
        ("05:49:56.604908", "10.0.0.3.5001",  "10.0.0.2.54880", 2)).toDF("column0", "column1", "column2", "label")

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
        .withColumn("extra",        filter($"split_values", _ =!= ""))

      // other ...
      dept
        .withColumn("split_values", array_remove(split($"VALUES", concat(lit("\\").cast(StringType), $"Delimiter")), ""))
        .show(truncate = false)


      // tutorial solution
      dept
        .withColumn("split_values", expr("""split(values, concat("\\", delimiter))"""))
        .withColumn("extra",        array_remove('split_values, "")).show(truncate = false)
    }
  }
}








