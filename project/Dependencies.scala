import sbt._

/*
 * Shamelessly stolen from spray.io
 */
object Dependencies {
  def exclusions (exclusions: Tuple2[String, String]*): Seq[ExclusionRule] = exclusions map (e => ExclusionRule(e._1, e._2))

  val Repositories = Seq(
    "Spray" at "http://repo.spray.io/",
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    "Despegar Releases" at "http://nexus.despegar.it/nexus/content/groups/public-release/",
	"clojars" at "http://clojars.org/repo/"
  )
  val hectorExclusions = exclusions(("commons-logging", "commons-logging"), ("javax.servlet", "servlet-api"), ("org.slf4j", "slf4j-log4j12"))

  val logback = "ch.qos.logback" % "logback-classic" % "1.0.13"
  val jclOverSlf4j = "org.slf4j" % "jcl-over-slf4j" % "1.7.5"
  val log4jOverSlf4j = "org.slf4j" % "log4j-over-slf4j" % "1.7.5"
  val scalaCompiler = "org.scala-lang" % "scala-compiler" % "2.11.6"
  //val jline =  "org.scala-lang" % "jline" % "2.11.0-M3"
  val scalajTime = "org.scalaj" % "scalaj-time_2.11" % "0.8"
  val jodaTime = "joda-time" % "joda-time" % "2.1"
  val jodaConver =  "org.joda" % "joda-convert" % "1.2"
  val scalaLogging = "com.typesafe.scala-logging" % "scala-logging-slf4j_2.11" % "2.1.2"
  val junit = "junit" % "junit" % "4.11"
  val scalatest = "org.scalatest" %% "scalatest" % "2.2.1"
  val mockito = "org.mockito" % "mockito-core" % "1.9.5"
  val easymock = "org.easymock" % "easymock" % "3.1"
  val scalaUtil = "com.despegar" % "scala-util_2.11" % "0.3.0"
  val logbackUtil = "com.despegar" %% "scala-logback-util" % "0.3.1"
  val getOpt = "org.rogach" % "scallop_2.11" % "0.9.5"
  val commonsCodec = "commons-codec" % "commons-codec" % "1.3"
  val httpClient = "org.apache.httpcomponents" % "httpclient" % "4.3.1"
  val mail = "javax.mail" % "mail" % "1.4.1"
  val liftJson = "net.liftweb" %% "lift-json-ext" % "2.6"
  val scalatra =   "org.scalatra" %% "scalatra" % "2.3.1"
  val jetty = "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910"
  val javax =   "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016"
  val asyncHttpClient = "com.ning" % "async-http-client" % "1.8.14"
  val maddonaConfig = "com.despegar.sbt" %% "madonna-configuration" % "0.0.4"
  val typesafeConfig = "com.typesafe" % "config" % "1.2.1"
  val mysql = "mysql" % "mysql-connector-java" % "5.1.12"


  val scalalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % "2.2.7"
  val h2 = "com.h2database" % "h2" % "1.4.187"


  val Compile = Seq(commonsCodec, logback, scalaCompiler, /*jline,*/ scalajTime, jodaTime, jodaConver,
    scalaLogging, scalaUtil, logbackUtil, getOpt, httpClient, mail, liftJson, scalatra, jetty, javax, asyncHttpClient, 
    maddonaConfig, typesafeConfig, scalalikejdbc, h2, mysql)

  val Test = Seq(junit, scalatest, mockito, easymock, asyncHttpClient) map (_ % "test")

  val All = Compile ++ Test
}
