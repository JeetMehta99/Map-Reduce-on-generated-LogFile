package UtilsTesting

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import com.typesafe.config.{Config, ConfigFactory}
class PatternTesting extends AnyFlatSpec with Matchers {
  behavior of "configuration parameters module"

  it should "obtain the Pattern" in {
    val config: Config = ConfigFactory.load("application.conf")
    config.getString("randomLogGenerator.Pattern") shouldBe String
  }
}
