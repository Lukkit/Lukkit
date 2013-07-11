package unwrittenfun.bukkit.lukkit.consolecommandsender;

import org.bukkit.command.ConsoleCommandSender;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.commandsender.CommandSenderObject;
import unwrittenfun.bukkit.lukkit.conversable.ConversableObject;

public class ConsoleCommandSenderObject extends LukkitObject {
	public ConsoleCommandSender consolecommandsender;

	public ConsoleCommandSenderObject(ConsoleCommandSender c) {
		consolecommandsender = c;

		extendWith(new CommandSenderObject(consolecommandsender));
		extendWith(new ConversableObject(consolecommandsender));
	}
	
	@Override
	public Object getObject() {
		return consolecommandsender;
	}
}
