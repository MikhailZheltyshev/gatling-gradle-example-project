package baseConfig

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BaseSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")
    .proxy(Proxy("localhost", 8888).httpsPort(8888))
}