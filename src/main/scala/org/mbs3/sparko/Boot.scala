package org.mbs3.sparko

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.util.Timeout
import scala.concurrent.duration._
import akka.pattern.ask
import org.mbs3.sparko.bot.SparkoBot

object Boot extends App {
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")
  
  println("Starting bot in new thread")
  new Thread(new SparkoBot()).start()
  
  println("Proceeding with http server")
  // create and start our service actor
  val service = system.actorOf(Props(classOf[MyServiceActor]), "demo-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
  println("HTTP Server started")
}
