package sparkk

import org.apache.spark.sql.types.{BooleanType, DoubleType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{functions => F}
import util.Spark._
import util.Spark.UdfFunctions._
import util.InitSession._
import spark.implicits._
import org.apache.spark.sql.functions._


object LearnSparkBook {

  def main(args: Array[String]): Unit = {
    someExercise()
  }


  def someExercise(): Unit = {
    val departue = readCsv("/Users/hryhorii/Documents/projects/win-won/src/main/resources/sparkk/learning-spark/departuredelays.csv")
    val airport  = readTxt("/Users/hryhorii/Documents/projects/win-won/src/main/resources/sparkk/learning-spark/airport-codes-na.txt")

    departue.printSchema()
    airport.printSchema()

    departue.show(truncate = false)
    airport.show(truncate = false)
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
