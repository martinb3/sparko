package org.mbs3.sparko.bot.commands

import org.pircbotx._
import org.pircbotx.hooks.types.GenericMessageEvent

object HelloWorldCommand extends IrcCommand[PircBotX] {
  def matches(cmd: String) : Boolean = {
    val pattern = "^[H|h]ello$".r
    
    !pattern.findFirstIn(cmd).isEmpty
  }
  def handle(cmd: String, event: GenericMessageEvent[PircBotX]) : Unit = {
    event.respond("Hello world")
  }
}

object GoodbyeCommand extends IrcCommand[PircBotX] {
  def matches(cmd: String) : Boolean = {
    val pattern = "^[G|g]oodbye$".r
    
    !pattern.findFirstIn(cmd).isEmpty
  }
  def handle(cmd: String, event: GenericMessageEvent[PircBotX]) : Unit = {
    if(!event.getUser().getNick().equals("smithmb")) {
      
      println("Tricky user "+event.getUser().getNick()+" tried to send me a goodbye!")
      return
    }
    
    println("Exiting by request " + event)
    event.respond("Goodbye")
    val bot = event.getBot()
    bot.stopBotReconnect()
    bot.sendIRC().quitServer()
  }
}