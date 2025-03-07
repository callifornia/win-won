package spark

trait Tutorial {

  // Spark
  /*
  *
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
  *
  *
  * */
}
