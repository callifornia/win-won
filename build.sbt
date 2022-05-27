val AkkaVersion = "2.6.19"
val KafkaVersion = "2.8.0"

scalaVersion := "2.13.8"
libraryDependencies ++= List(
  "com.lihaoyi" %% "fansi" % "0.3.1",
  "org.scalameta" %% "munit" % "0.7.29" % Test,

// not compiled with a scala 2.13.8 as they have dependency issue
//  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
//  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,

  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.apache.kafka" %% "kafka" % KafkaVersion,
  "org.apache.kafka" % "kafka-clients" % KafkaVersion
)

testFrameworks += TestFramework("munit.Framework")