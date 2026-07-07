package asdasd

import org.apache.hadoop.yarn.webapp.hamlet2.HamletSpec.Scope.col
import org.apache.spark.sql.SparkSession
import spark.ReadWriteData._
import spark.util.InitSession.{spark, _}
import spark.implicits._
import org.apache.spark.sql.functions._


object Check {

  case class Guitar(id: Long, model: String, make: String, guitarType:String)
  case class GuitarPlayers(id: Long, name: String, guitars: Seq[Long], band: Long)
  case class Bands(id: Long, name: String, hometown: String, year: Long)

  def main(args: Array[String]): Unit = {
    exercise_1()
  }


  def exercise_1()(implicit spark: SparkSession): Unit = {
    val guitarDF = readJson("src/main/resources/spark/daniel/data/guitars.json").as[Guitar].alias("g")
    val guitarPlayerDF = readJson("src/main/resources/spark/daniel/data/guitarPlayers.json").as[GuitarPlayers].alias("gp")
    val guitarPlayerGuitarsExplodedDF = guitarPlayerDF.withColumn("guitar", explode($"guitars"))


    val result_1 = guitarDF.join(guitarPlayerGuitarsExplodedDF,  guitarDF("id") === guitarPlayerGuitarsExplodedDF("guitar"))

    val result_2_inner = guitarPlayerDF.joinWith(guitarDF, array_contains($"gp.guitars", $"g.id"), "outer")
//    val result_2_outer = guitarDF.join(guitarPlayerDF, guitarPlayerDF("guitars").contains(guitarDF("id")))


    guitarDF.show(false)
    guitarPlayerDF.show(false)
    result_2_inner.show(false)
//    guitarPlayerGuitarsExplodedDF.show(false)
//    result_1.show(false)
  }
}
