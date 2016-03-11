import org.scalatra.LifeCycle
import javax.servlet.ServletContext
import com.despegar.patroller.controllers.ReportController
import com.despegar.patroller.controllers.InfrastructureController
import com.despegar.patroller.controllers.ReputationFilesUploader


class ScalatraBootstrap extends LifeCycle {
    override def init(context: ServletContext) {
        context mount (new ReportController, "/patroller")
        context mount (new InfrastructureController, "/")
        context mount (new ReputationFilesUploader, "/patroller/rfu")
    }
}