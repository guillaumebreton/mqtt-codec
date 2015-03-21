

import sbt._
import sbt.Keys._

object MQTTCodecBuild extends Build{


  lazy val buildSettings = Seq(
    organization        := "octalmind",
    version             := "1.0-SNAPSHOT",
    scalaVersion        := Dependencies.scalaVersion,
    crossScalaVersions  := Dependencies.crossScala
  )

  lazy val root = Project(
    id = "mqtt-codec",
    base = file("."),
    settings = buildSettings ++ Formatting.formatSettings ++ Publish.publishSetting ++ Seq(
      libraryDependencies ++= Dependencies.compile ++ Dependencies.test,
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


    )
  )
}

