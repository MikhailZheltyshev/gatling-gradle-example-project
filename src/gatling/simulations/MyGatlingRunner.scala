import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import simulations.CodeReuseWithObjects

object MyGatlingRunner {

  def main(args: Array[String]): Unit = {

    val simClass = classOf[CodeReuseWithObjects].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)

    Gatling.fromMap(props.build)
  }

}
