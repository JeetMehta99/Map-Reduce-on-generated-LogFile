name := "LogFileGenerator"

version := "0.1"

scalaVersion := "3.0.2"

val logbackVersion = "1.3.0-alpha10"
val sfl4sVersion = "2.0.0-alpha5"
val typesafeConfigVersion = "1.4.1"
val apacheCommonIOVersion = "2.11.0"
val scalacticVersion = "3.2.9"
val generexVersion = "1.0.2"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-core" % logbackVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.slf4j" % "slf4j-api" % sfl4sVersion,
  "com.typesafe" % "config" % typesafeConfigVersion,
  "commons-io" % "commons-io" % apacheCommonIOVersion,
  "org.scalactic" %% "scalactic" % scalacticVersion,
  "org.scalatest" %% "scalatest" % scalacticVersion % Test,
  "org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test,
  "com.typesafe" % "config" % typesafeConfigVersion,
  "org.apache.hadoop" % "hadoop-client" % "2.2.0",
  "org.apache.hadoop" % "hadoop-core" % "1.2.1",
  "com.github.mifmif" % "generex" % generexVersion,
  "joda-time" % "joda-time" % "2.10.12"

)


mainClass in (Compile, packageBin) := Some("Main1")
mainClass in (Compile, packageBin) := Some("Main2")
mainClass in (Compile, packageBin) := Some("Main3")
mainClass in (Compile, packageBin) := Some("Main4")
assemblyJarName in assembly := "jeetmehta_CS441.jar"
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}