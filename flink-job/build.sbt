name := "flink-job"

version := "0.1"

scalaVersion := "2.12.7"

val flinkVersion = "1.10.0"

libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion,
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion)

libraryDependencies += "com.twitter" % "hbc" % "2.2.0"
libraryDependencies += "org.apache.flink" % "flink-connector-twitter_2.12" % "1.10.0"