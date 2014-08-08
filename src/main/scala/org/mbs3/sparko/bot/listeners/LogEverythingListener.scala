package org.mbs3.sparko.bot.listeners

import org.pircbotx._
import org.pircbotx.hooks._
import org.pircbotx.hooks.events._

class LogEverythingListener extends Listener[PircBotX] {
	override def onEvent(event: Event[PircBotX]): Unit = {
	  println(event.toString)
	}
}
