package spark

trait Tutorial {

  // Spark
  /*
  *
  * Lazy evaluation   - spark waits until the last moment to execute the DF transformation
  * Planing           - before running any code spark compiles the DF transformation into a graph
  *     logical plan  - DF dependency graph + narrow/wide transformation sequence
  *     physical plan - optimized sequence of steps for nodes in the cluster
  *     optimization
  *
  * Transformation vs Actions
  *     transformation - describe how new Data Frame are obtained
  *     action         - actually start executing the code (show(), count())
  *
  * DataFrame - parquet default data storage
  *           - immutable
  *
  * * narrow-transformation - Data frame are split into the partition in between nodes in the cluster.
  *                         When we select any numbers of column from DataFrame those columns are going to be selected on every
  *                         partition on every node in the cluster. After select we will obtain new DataFrame and this will
  *                         be reflected on each node in the cluster. Every partition in the original DataFrame has exectly one
  *                         corresponding output partiotion in resulting DataFrame
  *
  * Projection - ability to create new DataFrame from another DataFrame
  *
  * */
}
