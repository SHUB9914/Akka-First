package WordCount

import akka.actor.{Actor, Props}

import scala.io.Source

/**
  * Created by knoldus on 20/3/17.
  */

class LineProcess extends Actor{

  var totalLines = 0
  var processedLine =0
  var totalWords = 0

  override def receive ={

    case file:String => Source.fromFile(file).getLines.foreach{

      line=>  context.actorOf(Props[WordCount]) ! line

    }

    case Processed(words) => totalWords+=words
    case result() => sender ! totalWords
  }
}