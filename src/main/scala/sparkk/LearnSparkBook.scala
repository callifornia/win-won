package sparkk

import org.apache.spark.sql.types.{BooleanType, DoubleType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{functions => F}
import util.Spark._
import util.Spark.UdfFunctions._
import util.InitSession._
import spark.implicits._

object LearnSparkBook {

  def main(args: Array[String]): Unit = {
    air()
  }


  def air(): Unit = {
    val df = readCsv("src/main/resources/spark/learning-spark/departuredelays.csv")
    df.show()
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
