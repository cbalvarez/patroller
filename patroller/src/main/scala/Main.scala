import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import org.eclipse.jetty.servlet.DefaultServlet
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.FileAppender
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.Level
import com.despegar.sbt.madonna.MadonnaConf


object Main extends App{
  def setLog() = {
    val logConfig =  MadonnaConf.config.getConfig("log");
    val c = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
    val d = c.asInstanceOf[ch.qos.logback.classic.Logger]
    d.detachAndStopAllAppenders()
    
    d.setLevel(Level.DEBUG)
    val ple = new PatternLayoutEncoder()
    ple.setPattern("%date|%level %msg%n")
    ple.setContext(d.getLoggerContext())
    ple.start()

    val fileAppender = new FileAppender[ILoggingEvent]();
    fileAppender.setFile(logConfig.getString("path"));
    fileAppender.setEncoder(ple);
    fileAppender.setContext(d.getLoggerContext());
    fileAppender.start();

    d.addAppender(fileAppender)
  }

  val server = new Server()
  val connector = new SelectChannelConnector()
  connector.setPort(9290)
  server.addConnector(connector)
  val context: WebAppContext = new WebAppContext()
  context setContextPath "/"
  context.setResourceBase("src/main/webapp")
  context.addEventListener(new ScalatraListener)
  context.addServlet(classOf[DefaultServlet], "/")
  context.setServer(server)
  server.setHandler(context)
  setLog()
  try {
    server.start()
    server.join()
  } catch {
    case e: Exception => {
      e.printStackTrace()
      System.exit(1)
    }
  }

}