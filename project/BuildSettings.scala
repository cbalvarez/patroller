import sbt._
import Keys._

object BuildSettings {

  val ScalacOptions = Seq("-feature", "-unchecked", "-deprecation", "-language:existentials", "-language:reflectiveCalls")

  lazy val basicSettings = Seq(
    organization := "com.despegar",
    scalaVersion := "2.11.6",
    resolvers ++= Dependencies.Repositories,
    scalacOptions := ScalacOptions,
    crossPaths := false,
publishTo := {
  if(version.value.endsWith("SNAPSHOT"))
    Some("Nexus snapshots" at "http://nexus.despegar.it:8080/nexus/content/repositories/snapshots/")
  else
    Some("Nexus releases" at "http://nexus.despegar.it:8080/nexus/content/repositories/releases/")
}  ) ++ net.virtualvoid.sbt.graph.Plugin.graphSettings

  lazy val runSettings = basicSettings ++ Seq(
    javaOptions in run ++= Seq("-Djava.library.path=/usr/local/lib", "-Xmx4G", "-Xms512M", "-XX:PermSize=256m", "-Dnet.sf.ehcache.skipUpdateCheck=true"),
    fork in run := true,
    connectInput in run := true,
    outputStrategy := Some(StdoutOutput)
  )
}

