package org.mbs3.sparko

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import org.mbs3.sparko.bot.SparkoBot

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val myRoute =
    get {
      path("hit") {
          respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            println("attempted to hit myself")
        	SparkoBot.say("i hit myself")
            <html>
              <body>
                <h1>Say hello to <i>world</i>!</h1>
              </body>
            </html>
          }
        }
      } ~
      path("speak") {
        parameter('text) { text =>
	    	complete { 
	    	  SparkoBot.say(text)
	    	  (s"The color is '$text'")
	    	}
        }
      }
    }
}