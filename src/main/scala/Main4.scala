
import HelperUtils.Mapper_Job4
import org.slf4j.LoggerFactory
import com.typesafe.config.Config
class Main4 {}
object Main4 {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("Mapper & Reducer jobs 4")
    Mapper_Job4.runJob4(args)
  }
}
