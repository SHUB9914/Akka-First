package BookMyShow

import akka.actor.{Actor, ActorRef}

/**
  * Created by knoldus on 20/3/17.
  */
class UserActor(ref :ActorRef ) extends Actor{
  override def receive = {

    case seat : String =>  ref ! seat
  }
}
