
package AkkaHttpDEMO

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.{RequestContext, Route}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import scala.io.StdIn

object AkkaRouting {


  def main(args: Array[String]) {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    def firstRoute:Route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }

    def secondRoute: Route =
      path("hello"/Segment) { name =>
        get {
          complete("say hello to "+name)
        }
      }


    def thirdRoute : Route =
      path("my"/"route") {
        parameters("id", "password") { (id, password) =>
        get {
          if(id.toInt==1 && password.toInt==1234) complete("Login successFully") else complete("sorry you are not authorized")
        }
      }
    }

    def fouthRoute: Route =  get {
      pathPrefix("my" / "route") {
        pathEnd {
          complete("Say hello to akka-http")
        } ~ path(Segment) { (name) =>
          complete("Say hello to " + name)
        }
      }
    }

    def redirectingRoute: Route =
      path("login") {
        get {
          val url = "http://localhost:8080/my/route"
          val response = Http().singleRequest(HttpRequest(uri = url + "?id=1&password=1234"))
          complete(response)
        }
      }


    val route = firstRoute ~ secondRoute ~ thirdRoute ~ fouthRoute ~ redirectingRoute

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ ⇒ system.terminate()) // and shutdown when done

  }
}