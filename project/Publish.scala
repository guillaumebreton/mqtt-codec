import sbt._
import sbt.Keys._

object Publish{

  lazy val publishSetting = Seq(
    publishTo := {
    val nexus = "https://oss.sonatype.org/"
    val (name, path) = if (isSnapshot.value) ("snapshots", "content/repositories/snapshots")
                       else ("releases", "service/local/staging/deploy/maven2")
    Some(name at nexus + path)
  },

  publishMavenStyle := true,

  publishArtifact in Test := false,

  pomIncludeRepository := { _ => false },

  pomExtra := {
    <scm>
      <url>https://github.com/guillaumebreton/mqtt-codec</url>
      <connection>scm:git:git@github.com:guillaumebreton/mqtt-codec.git</connection>
    </scm>
    <developers>
      <developer>
        <id>guillaumebreton</id>
        <name>Guillaume Breton</name>
      </developer>
    </developers>
  }
  )

}
