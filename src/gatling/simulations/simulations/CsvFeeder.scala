package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._


class CsvFeeder extends BaseSimulation {
  val csvFeeder: BatchableFeederBuilder[String]#F = csv("feeders/gamesList.csv").circular

  def getSpecificVideoGame(): ChainBuilder = {
    repeat(10) {
      feed(csvFeeder)
        .exec(http("Get specific game")
          .get("videogames/${gameId}")
          .check(jsonPath("$.name").is("${gameName}"))
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
