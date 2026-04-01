package spark.util

import org.apache.spark.sql.SparkSession

trait InitSession {

  /*

        - select operation can bring: java.io.IOException
            use them in the end => looks like it can fix
        - use ".cache()" for DataFrame in case: java.io.IOException: Connection reset by peer "

     */
  implicit val spark: SparkSession = SparkSession
    .builder()
    .appName("lessons")
    .master("local[*]")
    .config("spark.sql.extensions",            "io.delta.sql.DeltaSparkSessionExtension")
    .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
    .config("spark.network.timeout", "600s")  // Increase network timeout
    .config("spark.rpc.askTimeout", "600s")   // Increase RPC timeout
    .config("spark.rpc.retry.max", "10")     // Increase RPC retries
    .config("spark.io.buffer.size", 32768)
    .config("spark.sql.debug.maxToStringFields", 150)
    .config("spark.executor.heartbeatInterval", "60s")
    .config("spark.executor.memory", "12g")
    .config("spark.driver.memory", "12g")
    .config("spark.executor.cores", "2")
    .config("spark.num.executors", "10")
    .config("spark.driver.host", "127.0.0.1")
    .config("spark.driver.bindAddress", "127.0.0.1")
    .config("spark.driver.port", "0")
    .getOrCreate()
}

object InitSession extends InitSession
