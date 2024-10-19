val AkkaVersion = "2.6.5"
val KafkaVersion = "2.8.0"
val ZioVersion = "1.0.12"
val CatsEffectVersion = "3.3.12"

scalaVersion := "2.13.8"
libraryDependencies ++= List(
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "org.scalactic" %% "scalactic" % "3.2.19",
  "com.lihaoyi" %% "fansi" % "0.3.1",
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "dev.zio" %% "zio" % ZioVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.typelevel" %% "cats-effect" % CatsEffectVersion,
  "org.typelevel" %% "cats-core" % "2.8.0",
  "org.typelevel" %% "cats-free" % "2.8.0",

  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
//  "com.typesafe.akka" %% "akka-cluster-typed" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-cluster-sharding-typed" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion,

//  "com.typesafe.akka" %% "akka-persistence-typed" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion,
//  "com.lightbend.akka" %% "akka-projection-core" % "1.2.4",
//  "com.lightbend.akka" %% "akka-projection-eventsourced" % "1.2.4",
//  "com.lightbend.akka" %% "akka-projection-cassandra" % "1.2.4",
//  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-persistence-cassandra" % "1.0.5"


  // journal dependencies
//  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
//  "org.iq80.leveldb" % "leveldb" % "0.7"




  // not compiled with a scala 2.13.8 as they have dependency issue
  //  "org.apache.kafka" %% "kafka" % KafkaVersion,
  //  "org.apache.kafka" % "kafka-clients" % KafkaVersion,
  //  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,

  //  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
)

testFrameworks += TestFramework("munit.Framework")
