import sbt._
import sbt.Keys._

object Dependencies{

  val crossScala = Seq("2.11.5", "2.10.4")
  val scalaVersion = crossScala.head

  val compile = Seq(
    "org.scodec" %% "scodec-core" % "1.7.0"
  )

  val test = Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )

}
