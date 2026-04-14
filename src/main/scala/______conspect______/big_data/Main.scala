package ______conspect______.big_data

import io.delta.tables.DeltaTable
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.types.StringType
import spark.util.InitSession._
import spark.implicits._

import scala.util.Random


object Main {

  def main(args: Array[String]): Unit = {
//    println("Hello world ...")
////    val data = spark.range(0, 50)
////    data.show()
////    data.repartition(1).write.mode("overwrite").csv("src/main/resources/spark/range")
////    data.repartition(1).write.format("delta").mode("overwrite").save("src/main/resources/spark/range-delta")
////    mergeDeltaData()
////    readCommits()
////    mergeDelta2()
////    mergeDeltaData()
////    schemaEvolution()
////    schemaEvolution2()
////    checkPerformance()
////    describe()
////    exercise()
////    readVersions()
////    resetToVersion()
////    createData()
//    readLatest("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-5")
//    upgradeColumnName()
//    deleteColumn()
//    deleteRows()
//    mergeColumns()
//    zOrder()
//    zOrder()
//    partiotionBy()
//    optimize2()
//    zOrder_2()
    println(generateWithRepeat(1, 5))
  }



  def generateWithRepeat(from: Int, to_2: Int): Seq[(Int, String)] =
    Random.shuffle {
      for {
        index        <- (from to to_2)
        index_plus   <- (from to index)
      } yield (index, "age_" + index + "_" +index_plus)
    }


  def zOrder_2()(implicit spark: SparkSession): Unit = {
    def genData(from: Int, to: Int): Seq[(Int, String)] = (from until to).map(index => (index, "age_" + index))


    DeltaTable
      .forPath("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-7")
      .toDF
      .show()
  }


  def optimize2()(implicit spark: SparkSession): Unit = {
    DeltaTable
      .forPath("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-7")
      .optimize()
      .executeCompaction()
  }

  def partiotionBy()(implicit spark: SparkSession): Unit = {
    def genData(from: Int, to: Int): Seq[(Int, String)] = (from until to).map(index => (index, "age_" + index))
    val data = genData(0, 10).foldLeft(Seq.empty[(Int, String, String)]){
      case (acc, (index, age)) if index % 2 == 0 => acc :+ (index, age, "ua")
      case (acc, (index, age))                   => acc :+ (index, age, "fr")
    }

    data
      .toDF("id", "age", "region")
      .write
      .format("delta")
      .partitionBy("region")
      .save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-7")
  }


  def zOrder()(implicit spark: SparkSession): Unit = {
    //    val data = spark.range(0, 10)
    //    data.write.format("delta").save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-6")
    val writtenData = DeltaTable.forPath("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-6")
    writtenData.toDF.show()
    writtenData.optimize().executeZOrderBy("id")
    val optimizedData = DeltaTable.forPath("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-6")
    optimizedData.toDF.show()
  }


  def cloneData()(implicit spark: SparkSession): Unit = {
    spark.sql("""
        CREATE TABLE deep_clone_table
        DEEP CLONE delta.`/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-6`
        LOCATION '/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/deep-clone-tables'
    """)

    spark.sql(
      """
        SELECT * FROM deep_clone_table
        """).show()
  }


  // write merge columns ...
  def mergeColumns()(implicit spark: SparkSession): Unit = {
    def genData(from: Int, to: Int): Seq[(Int, String)] = (from until to).map(index => (index, "age_" + index))
    val data = genData(1, 10).toDF("id", "age")
    data.coalesce(1).write.format("delta").save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-5")
    val writtenData = DeltaTable.forPath(spark, "/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-5")
    val data_2 = genData(5, 15).toDF("id", "age")
    writtenData.as("main")
      .merge(data_2.as("second"), "main.id == second.id")
      .whenMatched()
      .update(Map("main.age" -> lit("foo").cast(StringType)))
      .whenNotMatched()
      .insertExpr(Map(
        "main.id" -> "second.id",
        "main.age" -> "second.age"
      ))
      .execute()
  }



  def deleteRows()(implicit spark: SparkSession): Unit = {
    val data = spark.read.format("delta").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-4")
    data.show()
  }



  def upgradeColumnName()(implicit spark: SparkSession): Unit = {
    spark.sql("""
      ALTER TABLE delta.`/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-4`
      SET TBLPROPERTIES (
        'delta.columnMapping.mode' = 'name'
      )
    """)
  }



  def deleteColumn()(implicit spark: SparkSession): Unit = {
//    val data = spark.read.format("delta").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-4")
    spark.sql("ALTER TABLE delta.`/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-4` DROP COLUMN name")
//    data.show()
  }



  def createData()(implicit spark: SparkSession): Unit = {
    import spark.implicits._
    val data =  (1 to 5).map(index => (index, s"name_" + index)).toSeq.toDF("id", "name")
    data.write.format("delta").save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-4")
  }



  def parquetToDeltaLake()(implicit spark: SparkSession): Unit = {
    val data = DeltaTable.convertToDelta(spark, "path/deltaLake...")
  }



  def resetToVersion()(implicit spark: SparkSession): Unit = {
    val data = DeltaTable.forPath("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-3")
    data.restoreToVersion(0)
  }



  def readLatest(path: String)(implicit spark: SparkSession): Unit = {
    val data = spark.read.format("delta").load(path)
    data
      .sort("id")
      .show()
  }



  def readVersions()(implicit spark: SparkSession): Unit = {
    val data_0 = spark.read.format("delta").option("versionAsOf", "0").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-3")
    val data_1 = spark.read.format("delta").option("versionAsOf", "1").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-3")
    val data_2 = spark.read.format("delta").option("versionAsOf", "2").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-3")

    data_0.show()
    data_1.show()
    data_2.show()
  }



  def exercise()(implicit spark: SparkSession): Unit = {
    val data = spark.range(0, 3)
    val data_2 = spark.range(4, 6)
    val data_3 = spark.range(7, 10)

    data.coalesce(1).write.format("delta").save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-3")
    data_2.coalesce(1).write.format("delta").mode("overwrite").save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-3")
    data_3.coalesce(1).write.format("delta").mode("overwrite").save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-3")
  }



  def describe()(implicit spark: SparkSession): Unit = {
    val data = spark.sql("DESCRIBE DETAIL delta.`/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2`")
    data.show(100, truncate = false)
  }



  def optimize()(implicit spark: SparkSession): Unit = {
    val data = DeltaTable.forPath("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2")
    data.optimize().executeCompaction()
  }



  def checkPerformance()(implicit spark: SparkSession): Unit = {
    val data = DeltaTable.forPath("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2")
//    data.history().select("version", "operation", "operationMetrics").show(false)
    data.history().show(false)
  }



  def schemaEvolution2()(implicit spark: SparkSession): Unit = {
    val data = spark.read.format("delta").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2")
    data
      .withColumn("foo", lit("foo_value").cast(StringType))
      .write
      .option("mergeSchema", true)
      .mode("overwrite")   // path already exists ...
//      .option("mergeSchema", true)
      .format("delta")
      .save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2")

    val data2 = spark.read.format("delta").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2")
    data2.show(100, truncate = false)
  }



  def schemaEvolution()(implicit spark: SparkSession): Unit = {
    val data = spark.range(0, 10)
    data.write.format("delta").save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2")
  }



  def readCommits()(implicit spark: SparkSession): Unit = {
    val data = spark.sql("DESCRIBE history delta.`/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta-2`")
//    val data_2 = spark.read
//      .option("timestampAsOf", "2026-04-02")
//      .load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta")
    data.toDF().show(100, truncate = false)
//    data_2.toDF().show(100, truncate = false)
  }



  def mergeDelta2()(implicit spark: SparkSession): Unit = {
    val mainDelta = DeltaTable.forPath(spark, "/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta")
    mainDelta.toDF.show(100, truncate = false)

    val newRange = spark.range(60, 70).toDF()

    mainDelta
      .as("main")
      .merge(newRange.as("second"), "main.id == second.id")
      .whenMatched()
      .updateExpr(Map(
        "id_version_2" -> "1"))
      .whenNotMatched()
      .insertExpr(Map(
        "id"           -> "second.id",
        "id_version_2" -> "second.id"))
      .execute()
  }



  def mergeDeltaData()(implicit spark: SparkSession): Unit = {
    spark
      .range(0, 50)
      .coalesce(1)
      .withColumn("id_version_2", $"id" * 2)
      .write
      .format("delta")
      .mode("OVERWRITE")
      .save("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta")

    val mainDelta = DeltaTable.forPath(spark, "/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta")
    mainDelta.toDF.show(100, truncate = false)

    val newRange = spark.range(45, 65).toDF()

    mainDelta
      .as("main")
      .merge(newRange.as("second"), "main.id == second.id")
      .whenMatched()
      .updateExpr(Map(
        "id_version_2" -> "0"))
      .whenNotMatched()
      .insertExpr(Map(
        "id"           -> "second.id",
        "id_version_2" -> "second.id"))
      .execute()

    val updatedData = spark.read.format("delta").load("/Users/hryhorii/Desktop/projects/win-won/src/main/resources/spark/range-delta")
    updatedData
      .sort("id")
      .show(100, truncate = false)
  }
}
