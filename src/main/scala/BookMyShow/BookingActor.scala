package BookMyShow

import akka.actor.Actor

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 20/3/17.
  */

class BookingActor extends Actor{
  var seats = ListBuffer("A","B","C","D")

  override def receive = {
    case seatnumber:String =>
      if(seats contains(seatnumber)){
      seats -=seatnumber
      println("booked successfully")
    }
    else {
      println("sorry this seat reserved already")
    }
  }
}