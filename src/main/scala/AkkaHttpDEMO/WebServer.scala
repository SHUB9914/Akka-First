package AkkaHttpDEMO

/**
  * Created by shubham on 8/2/17.
  */
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.scaladsl.server.{RequestContext, Route}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.io.StdIn
import scala.concurrent.Future

object WebServer {

  // domain model
  final case class Item(name: String)
  final case class Order(items: List[Item])

  // formats for unmarshalling and marshalling
  implicit val itemFormat = jsonFormat1(Item)
  implicit val orderFormat = jsonFormat1(Order)

  // (fake) async database query api
  def fetchItem(itemId: Long): Future[Option[Item]] = ???
  def saveOrder(order: Order): Future[Done] = ???

  def main(args: Array[String]) {

    // needed to run the route
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    // needed for the future map/flatmap in the end
    implicit val executionContext = system.dispatcher

    val getItem: Route =

      get {

        pathPrefix("item") {
          pathEnd {
            val url = "http://localhost:8080/my/route"
            val response = Http().singleRequest(HttpRequest(uri = url + "?name=shubhamAg&age=22"))
            // there might be no item for a given id
            println("---yessssssssss")
            complete(response)

            //          onSuccess(maybeItem) {
            //            case Some(item) => complete(item)
            //            case None       => complete(StatusCodes.NotFound)
            //          }

          } ~ path(Segment) { (targetId) =>
          complete("yesssssssssss----" + targetId)
        }
      }
  }

def myroute:Route = path("my"/"route") {

  parameters("name", "age") { (name, age) =>
    get {
      println(s"name---$name and age = $age")
      val str = s""" {"message":"saved","name":"shubham"}"""
      val res = HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, str))
      complete(res)

    }
  }
}

    def contextRoute:Route = path("my/cntx"){
      put{
        ctx: RequestContext =>{
          ctx.complete("")
        }

      }
    }
    val a =
      pathPrefix("my"/"route")  {

        pathEnd {
          get {
            complete("Say hello to akka-http")
          }
        } ~ path(Segment) { (name) =>
          complete("Say hello to"+name)
        }

      }

    val route = myroute ~ getItem
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ ⇒ system.terminate()) // and shutdown when done

  }
}