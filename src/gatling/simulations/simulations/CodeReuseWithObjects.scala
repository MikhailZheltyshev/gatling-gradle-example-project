package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

class CodeReuseWithObjects extends BaseSimulation {

  def getAllVideoGames(): ChainBuilder = {
    repeat(3) {
      exec(http("Get All Video Games")
        .get("videogames").check(status.is(200)))
    }
  }

  def getSpecificVideoGame(): ChainBuilder = {
    repeat(5) {
      exec(http("Get specific game")
        .get("videogames/1").check(status.is(200)))
    }
  }

  val scn: ScenarioBuilder = scenario("Vidoe Games Tests with Methods")
    .exec(getAllVideoGames())
    .pause(1)
    .exec(getSpecificVideoGame())
    .pause(2)
    .exec(getAllVideoGames())


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
