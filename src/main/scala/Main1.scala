
import HelperUtils.Mapper_Job1
import com.typesafe.config.Config
import org.slf4j.LoggerFactory
class Main1{}

object Main1{
  private val logger = LoggerFactory.getLogger(this.getClass)
  def main(args: Array[String]): Unit =
  {

      logger.info("Mapper & Reducer jobs 1")
      Mapper_Job1.runJob1(args)
    }
}
