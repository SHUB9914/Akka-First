package WordCount
import akka.actor.Actor

/**
  * Created by knoldus on 20/3/17.
  */

class WordCount extends Actor{

  override def receive = {
    case line :String if(line =="") => sender ! Processed(0)
    case line:String => val totalwords = line.split(" ").toList
      val words =  for(i<-totalwords if (i!="")) yield  i

      sender ! Processed(words.length)
  }
}
