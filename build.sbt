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
  "org.specs2" %% "specs2-core" % "2.4.15" % "test"
)

scalariformSettings
