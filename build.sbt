import scalariform.formatter.preferences._
import bintray.Keys._

name := "mqtt-codec"

version := "1.1.0"

organization := "octalmind"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.11.6", "2.10.5")

libraryDependencies ++= Seq(
  "org.scodec" %% "scodec-core" % "1.7.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

scalacOptions ++= Seq(
  "-language:implicitConversions",
  "-unchecked",
  "-feature",
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-Xfatal-warnings",
  "-Xlint:_",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard"
)

scalariformSettings
ScalariformKeys.preferences := FormattingPreferences()
    .setPreference(RewriteArrowSymbols, true)
    .setPreference(AlignParameters, true)
    .setPreference(AlignSingleLineCaseStatements, true)

// publishMavenStyle := false
bintrayPublishSettings
repository in bintray := "maven"
bintrayOrganization in bintray := None
