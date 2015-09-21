import sbt._
import com.despegar.sbt.madonna.Madonna._

madonnaSettings

publishTo := {
  if(version.value.endsWith("SNAPSHOT"))
    Some("Nexus snapshots" at "http://nexus.despegar.it:8080/nexus/content/repositories/snapshots/")
  else
    Some("Nexus releases" at "http://nexus.despegar.it:8080/nexus/content/repositories/releases/")
}

mainClass := Some("com.despegar.patroller.Main")

publish in ThisBuild <<= MadonnaKeys.tarPublish

publishLocal in ThisBuild <<= MadonnaKeys.tarPublishLocal

libraryDependencies ++= Seq("com.newrelic.agent.java" % "newrelic-agent" % "3.14.0" % "agent")
