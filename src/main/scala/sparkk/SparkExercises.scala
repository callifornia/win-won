package sparkk

import org.apache.spark.sql.types.StringType
import util.InitSession._

import java.util.regex.Pattern
//import util.Spark._
import spark.implicits._
import org.apache.spark.sql.functions._



object SparkExercises {

  def main(args: Array[String]): Unit = {
    SparkWorkshop.exercises_2()
  }


  object SparkWorkshop {

    // selecting the most important rows per assigned priority ...
    def exercises_2(): Unit = {
      val input = Seq(
        (1, "MV1"),
        (1, "MV2"),
        (2, "VPV"),
        (2, "Others")).toDF("id", "value")

      input.show()
      input.groupBy($"id").
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

      dept
        .withColumn("split_values", split($"VALUES", quoteRegex($"Delimiter")))
        .withColumn("extra",        array_remove($"split_values", ""))


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








