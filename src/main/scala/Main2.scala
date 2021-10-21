
import HelperUtils.Mapper_Job2
import org.slf4j.LoggerFactory
import com.typesafe.config.Config
class Main2 {}
object Main2 {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("Mapper & Reducer jobs 2")
    Mapper_Job2.runJob2(args)
  }
}
