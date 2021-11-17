package UtilsTesting

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import com.typesafe.config.{Config, ConfigFactory}
class ConfigTesting extends AnyFlatSpec with Matchers {
  behavior of "configuration parameters module"

  it should "obtain the Time " in {
    val config: Config = ConfigFactory.load("application.conf")
    config.getInt("randomLogGenerator.TimePeriod") shouldBe Int
  }

  behavior of "configuration parameters module" 
  it should "have duration" in {
    val config: Config = ConfigFactory.load("application.conf")
    config.getInt("randomLogGenerator.DurationMinutes") shouldBe Int
  }

  behavior of "configuration parameters module"
  it should "have Durations in minutes" in {
    val config: Config = ConfigFactory.load("application.conf")
    config.getInt("randomLogGenerator.DurationMinutes") shouldBe Int
  }

  behavior of "configuration parameters module"
  it should "have stringLength" in {
    val config: Config = ConfigFactory.load("application.conf")
    config.getInt("randomLogGenerator.MinString") shouldBe Int
  }
      

       
	
