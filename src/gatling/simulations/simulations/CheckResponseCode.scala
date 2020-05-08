package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

class CheckResponseCode extends BaseSimulation {

  val scn: ScenarioBuilder = scenario("Video Game DB")

    .exec(http("Get All Video Games - 1st call")
      .get("videogames").check(status.is(200)))

    .exec(http("Get specific game")
      .get("videogames/1").check(status.is(200)))

    .exec(http("Get All Video Games - 2nd call")
      .get("videogames").check(status.not(400), status.not(500)))

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
