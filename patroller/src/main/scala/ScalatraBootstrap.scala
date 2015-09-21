import org.scalatra.LifeCycle
import javax.servlet.ServletContext
import com.despegar.patroller.controllers.ReportController


class ScalatraBootstrap extends LifeCycle {
    override def init(context: ServletContext) {
        context mount (new ReportController, "/patroller")
    }
}