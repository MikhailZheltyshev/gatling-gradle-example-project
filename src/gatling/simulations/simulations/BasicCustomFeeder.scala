package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._


class BasicCustomFeeder extends BaseSimulation {
  var idNumbers: Iterator[Int] = (1 to 10).iterator

  val customFeeder: Iterator[Map[String, Int]] = Iterator.continually(Map("gameId" -> idNumbers.next()))

  def getSpecificVideoGame(): ChainBuilder = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("Get specific game")
          .get("videogames/${gameId}")
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn: ScenarioBuilder = scenario("Video Game CSV Feeder test")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpConf)
  )
}
