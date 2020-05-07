import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import simulations.AddPauseTime

object MyGatlingRunner {

  def main(args: Array[String]): Unit = {

    val simClass = classOf[AddPauseTime].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)

    Gatling.fromMap(props.build)
  }

}
