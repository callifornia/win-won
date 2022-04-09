scalaVersion := "3.1.1"
libraryDependencies ++= List(
  "com.lihaoyi" %% "fansi" % "0.3.1",
  "org.scalameta" %% "munit" % "0.7.29" % Test)

testFrameworks += TestFramework("munit.Framework")