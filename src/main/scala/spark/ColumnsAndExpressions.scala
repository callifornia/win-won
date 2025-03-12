package spark

import InitSparkSession._
import org.apache.spark.sql.functions.{col, column, expr}


object ColumnsAndExpressions extends App {

  lazy val carsDF = spark
    .read
    .option("inferSchema", "true")
    .json("src/main/resources/essential/cars.json")


  // Column
  val firstColumn = carsDF.col("Name")


  /* selecting(projection) */
  val carNamesDF = carsDF.select(firstColumn)


  /* various select methods */
  import spark.implicits._

  carsDF.select(
    carsDF.col("Name"),
    col("Name"),
    column("Name"),
    'Year,
    $"Horsepower",
    expr("Origin"))


  carsDF.select("Name", "Year")

  /* Expressions */
  val simpleExpression = carsDF.col("Weight_in_lbs")
  val simpleExpression2 = carsDF.col("Weight_in_lbs") / 2.2

  val carsWithWeightsDF = carsDF.select(
    col("Name"),
    col("Weight_in_lbs"),
    simpleExpression2.as("Weight_in_lbs"),
    expr("Weight_in_lbs / 2.2").as("Weight_in_lbs"))

  val carsWithSelectExprWeightsDF = carsDF.selectExpr(
    "Name",
    "Weight_in_lbs",
    "Weight_in_lbs / 2.2")

  // DF processing

  /* adding new column */
  val carWithKg3DF = carsDF.withColumn("Weight_in_kg_3", col("Weight_in_lbs") / 2.2)

  /* renaming a column */
  val carWithColumnRenamed = carsDF.withColumnRenamed("Weight_in_lbs", "Weight in pounds")

  /* careful with column names */
  carWithColumnRenamed.selectExpr("`Weight in pounds`")

  /* remove a column */
  carWithColumnRenamed.drop("Cylinders", "Displacement")

  /* filtering */
  val europinianCarDF = carsDF.filter(col("Origin") =!= "USA")
  val europinianCarDF2 = carsDF.where(col("Origin") =!= "USA")

  /* filtering with an expression String */
  val americanCarDF = carsDF.filter("Origin = 'USA'")
  val americanCarDF2 = carsDF.where("Origin = 'USA'")

  /* chain filters */
  val americanPowerfulCarsDF = carsDF.filter(col("Origin") === "USA").filter(col("Horsepower") > 150)
  val americanPowerfulCarsDF2 = carsDF.filter(col("Origin") === "USA" and col("Horsepower") > 150)
  val americanPowerfulCarsDF3 = carsDF.filter("Origin == 'USA' and Horsepower > 150")


  /* union = adding more rows */
  val moreCarsDF = spark.read.option("inferSchema", "true").json("src/main/resources/essential/more_cars.json")
  val allCarsDF = carsDF.union(moreCarsDF)

  /* distinct values */
  val allCountriesDF = carsDF.select("Origin").distinct()
  allCountriesDF.show()


}
