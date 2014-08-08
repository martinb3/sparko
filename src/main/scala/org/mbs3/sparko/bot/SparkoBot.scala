package org.mbs3.sparko.bot

import org.pircbotx._
import org.pircbotx.hooks._
import org.pircbotx.hooks.events._
import org.mbs3.sparko.bot.listeners.CommandListener
import org.mbs3.sparko.bot.listeners.LogEverythingListener

/**
 * SparkoBot class extends Runnable to allow bot to run on another thread
 */
class SparkoBot extends Runnable {
   	def run() { SparkoBot.startBot() }
   	def say(something:String) { SparkoBot.say(something) }
}

/**
 * Singleton object for actual bot configuration and connection
 */
object SparkoBot {

	val config = new Configuration.Builder()
	.setName("sparkobot")
	.setLogin("sparko@bot")
	.setCapEnabled(true)
	.setShutdownHookEnabled(false)
	.setServerHostname("irc.freenode.net")
	.addAutoJoinChannel("#sparko")
	.addListener(new LogEverythingListener)
	.addListener(new CommandListener)
	.buildConfiguration

	private val bot = new PircBotX(config);
    private def startBot() { bot.startBot() }
	
	def say(something:String) {
	  this.synchronized {
	    val dao = bot.getUserChannelDao()
	    val chan = dao.getChannel("#sparko")
	    val output = chan.send()
	    output.message(something)
	  }
	}

}