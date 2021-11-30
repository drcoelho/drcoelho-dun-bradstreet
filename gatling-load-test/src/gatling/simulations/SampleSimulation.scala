import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.{Duration, FiniteDuration}

class SampleSimulation extends Simulation {

  val scn = scenario("Activities").repeat(35000, "n") {
    exec(
      http("AddActivity-API")
        .post("http://localhost:8080/activities")
        .header("Content-Type", "application/json")
        .body(StringBody("""{"activity":{"userName":"Sam", "websiteName":"abc.com", "activityTypeDescription":"Viewed", "signedInTime": "01/13/2020"}}"""))
        .check(status.is(200))
    ).pause(Duration.apply(5, TimeUnit.MILLISECONDS))
  }

  setUp(scn.inject(atOnceUsers(30))).maxDuration(FiniteDuration.apply(10, "minutes"))


}
