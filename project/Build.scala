import sbt._
import Keys._
import com.despegar.sbt.{Versions, Tar}

object PatrollerBuild extends Build {

  lazy val root = Project("root", file(".")).aggregate(patroller)

  lazy val patroller = Project("patroller", file("patroller"))
    .settings(BuildSettings.runSettings: _*)
    .settings(libraryDependencies ++= Dependencies.All)
    .settings(Tar.tarSettings: _*)
    .settings(Tar.layout := TarLayouts.patroller.value)
    .settings(Versions.versionSettings : _*)
    //.settings(versionOnCompile)
    .settings(sbtrelease.ReleasePlugin.releaseSettings : _*)
    // Don't upload jar files.
    .settings(publishArtifact in (Compile, packageBin) := false)
    .settings(publishArtifact in (Compile, packageDoc) := false)
    .settings(publishArtifact in (Compile, packageSrc) := false)
}


