import sbt._
import Keys._
import com.despegar.sbt.{Versions, Tar}

object TarLayouts {
 private def allFilesIn(base: File, folder: String) = IO.listFiles(new File(base, folder))

 lazy val patroller = Def.task {

    val root = name.value
    val base = baseDirectory.value

    val libs = Attributed.data((externalDependencyClasspath in Runtime).value)
    val jar = (packageBin in Compile).value

    Map[String, Seq[File]](
      s"$root/lib" -> (libs :+ jar ),
      s"$root/bin" -> allFilesIn(base, "bin"))
  }
}

