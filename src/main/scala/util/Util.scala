package util

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.util.Properties


object Util {

  case class DbConnectionContainer(user: String,
                                   password: String,
                                   url: String,
                                   driver: String,
                                   properties: Properties)

  case class Connection(container: DbConnectionContainer, props: Properties)

  private val connection: Connection = {
    val user = "postgres"
    val password = "admin"
    val url = "jdbc:postgresql://localhost:5432/img"
    val driver = "org.postgresql.Driver"
    val properties = {
      val p = new java.util.Properties()
      p.setProperty("user", user)
      p.setProperty("password", password)
      p.setProperty("driver", driver)
      p
    }

    val container = DbConnectionContainer(
      user = user,
      password = password,
      url = url,
      driver = driver,
      properties = properties)

    Connection(container, properties)
  }



  object Spark {


    def write2DB(data: DataFrame, schema: String, tableName: String)(implicit spark: SparkSession): Unit =
      data
        .write
        .mode("append")
        .jdbc(connection.container.url + "?currentSchema=" + schema, tableName, connection.props)


    def readFromDB(schema: String, tableName: String)(implicit spark: SparkSession): DataFrame =
      spark
        .read
        .format("jdbc")
        .option("driver",   connection.container.driver)
        .option("url",      connection.container.url)
        .option("user",     connection.container.user)
        .option("password", connection.container.password)
        .option("dbtable",  s"$schema.$tableName")
        .load()


    def readJson(path: String)(implicit spark: SparkSession): DataFrame =
      spark
        .read
        .format("json")
        .option("inferSchema", true)
        .load(path)


    def readJson(path: String, schema: StructType)(implicit spark: SparkSession): DataFrame =
      spark
        .read
        .format("json")
        .schema(schema)
        .load(path)
  }
}
