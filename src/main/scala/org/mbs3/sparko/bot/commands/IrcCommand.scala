package org.mbs3.sparko.bot.commands

import org.pircbotx.hooks.Event
import org.pircbotx.hooks.types.GenericMessageEvent
import org.pircbotx.PircBotX

abstract class IrcCommand[T <: PircBotX] {
	def matches(cmd: String) : Boolean
	def handle(cmd: String, event: GenericMessageEvent[T]) : Unit
}