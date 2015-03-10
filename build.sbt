name := "mqtt-protocol"

version := "1.0"

resolvers += "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

scalaVersion := "2.11.6"

scalacOptions ++=  Seq(
  "-deprecation",
  "-unchecked",
  "-feature"
)

libraryDependencies ++= Seq(
  "org.scodec" %% "scodec-core" % "1.7.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

scalariformSettings
