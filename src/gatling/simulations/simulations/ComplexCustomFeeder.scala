package simulations

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import scala.util.Random

class ComplexCustomFeeder extends BaseSimulation {

  var idNumbers: Iterator[Int] = (21 to 30).iterator
  val rnd = new Random()
  val now: LocalDate = LocalDate.now()
  val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def randomString(length: Int): String = {
    rnd.alphanumeric
      .filter(_.isLetter)
      .take(length)
      .mkString
  }

  def getRandomDate(startDate: LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  val customFeeder: Iterator[Map[String, Any]] = Iterator.continually(Map(
    "gameId" -> idNumbers.next(),
    "name" -> s"Game - ${randomString(5)}",
    "releaseDate" -> getRandomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> s"Category - ${randomString(6)}",
    "rating" -> s"Rating - ${randomString(4)}"
  ))

  def getSpecificVideoGame(): ChainBuilder = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("Post New Game")
          .post("videogames/")
          .body(ElFileBody("bodies/newGameTemplate.json")).asJson
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn: ScenarioBuilder = scenario("Video Game JSON Feeder test")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpConf)
  )
}
