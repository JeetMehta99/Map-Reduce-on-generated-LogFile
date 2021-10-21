
import HelperUtils.Mapper_Job3
import org.slf4j.LoggerFactory
import com.typesafe.config.Config
class Main3 {}
object Main3 {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("Mapper & Reducer jobs 3")
    Mapper_Job3.runJob3(args)
  }
}
