package sparkk

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.{BooleanType, DateType, DoubleType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{functions => F}
import util.Spark._
import util.Spark.UdfFunctions._
import util.InitSession._
import spark.implicits._
import org.apache.spark.sql.functions._

import java.sql.Date


object LearnSparkBook {

  def main(args: Array[String]): Unit = {
    exercise()
  }

  def exercise(): Unit = {
    val data: Seq[(String, String, Int, Date, String)] = Seq(
      ("Alice", "Engineering", 90000, Date.valueOf("2019-03-15"), "M"),
      ("Bob", "Engineering", 85000, Date.valueOf("2020-06-01"), "M"),
      ("Charlie", "Engineering", 95000, Date.valueOf("2018-01-10"), "M"),
      ("Diana", "Engineering", 85000, Date.valueOf("2021-09-23"), "F"),
      ("Ethan", "Engineering", 78000, Date.valueOf("2022-02-14"), "M"),

      ("Fiona", "Sales", 60000, Date.valueOf("2017-11-05"), "F"),
      ("George", "Sales", 72000, Date.valueOf("2019-04-30"), "M"),
      ("Hannah", "Sales", 72000, Date.valueOf("2020-07-19"), "F"),
      ("Ian", "Sales", 58000, Date.valueOf("2022-01-01"), "M"),

      ("Julia", "HR", 65000, Date.valueOf("2016-05-20"), "F"),
      ("Kevin", "HR", 67000, Date.valueOf("2018-08-08"), "M"),
      ("Laura", "HR", 67000, Date.valueOf("2021-12-01"), "F"),

      ("Mike", "Marketing", 71000, Date.valueOf("2015-10-10"), "M"),
      ("Nina", "Marketing", 80000, Date.valueOf("2020-03-03"), "F"))

    val columns = Seq("name", "department", "salary", "hire_date", "gender")
    val df = data.toDF(columns: _*)

    val windowFunction =
      Window
        .partitionBy("department")
        .orderBy("department", "salary")
        .rowsBetween(Window.unboundedPreceding, Window.currentRow)

    val rankingFunctionDF =
      df
        .withColumn("row_number",   row_number().over(windowFunction))
        .withColumn("rank",         rank().over(windowFunction))
        .withColumn("denseRank",    dense_rank().over(windowFunction))
        .withColumn("percent_rank", percent_rank().over(windowFunction))
        .withColumn("ntile(2)",     ntile(2).over(windowFunction))

    lazy val analyticsFunctionDF =
      df
        .withColumn("lag",         lag($"hire_date", 1, lit("2000-01-01").cast(DateType)).over(windowFunction))
        .withColumn("lead",        lead($"hire_date", 1, lit("2000-01-01").cast(DateType)).over(windowFunction))
        .withColumn("first_value", first_value($"hire_date").over(windowFunction))
        .withColumn("last_value",  last_value($"hire_date").over(windowFunction))
        .withColumn("cume_dist",   cume_dist().over(windowFunction))

    // calculation depends on rowsBetween(Window.unboundedPreceding, Window.currentRow)
    val aggregateFunctionsDF =
      df
        .withColumn("sum",   sum("salary").over(windowFunction))
        .withColumn("avg",   avg("salary").over(windowFunction))
        .withColumn("min",   min("salary").over(windowFunction))
        .withColumn("max",   max("salary").over(windowFunction))
        .withColumn("count", count("salary").over(windowFunction))
        .withColumn("stddev", stddev("salary").over(windowFunction))



    // r - rank of the row
    // n - number of rows in the window partition
    // (r — 1) / (n — 1)


    df.show()
//    rankingFunctionDF.show(truncate = false)
//    analyticsFunctionDF.show(truncate = false)
    aggregateFunctionsDF.show(truncate = false)
//    aggregateFunctionsDF.explain("formatted")
  }


  def someExercise(): Unit = {
    //    val departue = readCsv("/Users/hryhorii/Documents/projects/win-won/src/main/resources/sparkk/learning-spark/departuredelays.csv")
    //    val airport  = readTxt("/Users/hryhorii/Documents/projects/win-won/src/main/resources/sparkk/learning-spark/airport-codes-na.txt")

    val conf = spark.conf.getAll
    val longestKey = conf.keySet.map(_.length).max

    val adoptKeyLeng: String => String =
      key => key + (1 to (longestKey - key.length)).map(_ => " ").mkString


    conf.foreach { case (key, value) => println(adoptKeyLeng(key) + " " + value) }

    //    departue.printSchema()
    //    airport.printSchema()
    //    departue.show(truncate = false)
    //    airport.show(truncate = false)
  }


  def air(): Unit = {
    val df = readCsv("/Users/hryhorii/Documents/projects/win-won/src/main/resources/sparkk/learning-spark/departuredelays.csv")
    //    spark.catalog.dropTempView("tmp_foo_one")
    //    spark.sql("DROP TABLE IF EXISTS tmp_foo_one")
    //    df.write.saveAsTable("tmp_foo_one")


    val result = df.where(
      $"origin" === "SFO" and
        $"destination" === "ORD" and
        $"delay" > 120)

    val withDelays =
      df.withColumn("Flight_Delays",
        when($"delay" > 360, "Very Long delays")
          .when($"delay" > 120 and $"delay" < 360, "Long delays")
          .when($"delay" > 60 and $"delay" < 120, "Short delays")
          .when($"delay" === 0, "No delays")
          .otherwise("Early"))

    //    spark.catalog.listDatabases()

    spark.catalog.listDatabases().show(truncate = false)
    //    spark.catalog.listColumns("tmp_foo_one").columns.toSeq.mkString("\n").foreach(println)

    //    withDelays
    //      .dropDuplicates("Flight_Delays")
    //      .show()

  }


  def fireCalls(): Unit = {
    val df = spark
      .read
      .option("header", value = true)
      .schema(fireSchema)
      .csv("src/main/resources/spark/learning-spark/sf-fire-calls.csv")

    df
      .select(F.sum("NumAlarms"), F.min("UnitSequenceInCallDispatch"), F.max("FirePreventionDistrict"))
      .show()
  }


  val fireSchema: StructType = StructType(
    Array(
      StructField("CallNumber", IntegerType, nullable = true),
      StructField("UnitID", StringType, nullable = true),
      StructField("IncidentNumber", IntegerType, nullable = true),
      StructField("CallType", StringType, nullable = true),
      StructField("CallDate", StringType, nullable = true),
      StructField("WatchDate", StringType, nullable = true),
      StructField("CallFinalDisposition", StringType, nullable = true),
      StructField("AvailableDtTm", StringType, nullable = true),
      StructField("Address", StringType, nullable = true),
      StructField("City", StringType, nullable = true),
      StructField("Zipcode", IntegerType, nullable = true),
      StructField("Battalion", StringType, nullable = true),
      StructField("StationArea", StringType, nullable = true),
      StructField("Box", StringType, nullable = true),
      StructField("OriginalPriority", StringType, nullable = true),
      StructField("Priority", StringType, nullable = true),
      StructField("FinalPriority", IntegerType, nullable = true),
      StructField("ALSUnit", BooleanType, nullable = true),
      StructField("CallTypeGroup", StringType, nullable = true),
      StructField("NumAlarms", IntegerType, nullable = true),
      StructField("UnitType", StringType, nullable = true),
      StructField("UnitSequenceInCallDispatch", IntegerType, nullable = true),
      StructField("FirePreventionDistrict", StringType, nullable = true),
      StructField("SupervisorDistrict", StringType, nullable = true),
      StructField("Neighborhood", StringType, nullable = true),
      StructField("Location", StringType, nullable = true),
      StructField("RowID", StringType, nullable = true),
      StructField("Delay", DoubleType, nullable = true)
    )
  )
}
