package WordCount
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global


case class result()
case class Processed(words:Int)

object  FileHandling  extends  App{

  val system = ActorSystem("FileSystem")
  val ref = system.actorOf(Props[LineProcess])
  val path = "/home/knoldus/Documents/Doc/abc.txt"

  ref ! path

  implicit val timeout = Timeout(1000 seconds)
  Thread.sleep(1000)
  val res = ref ? result()

  res.map(x=>println("total words="+x))
}
