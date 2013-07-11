package unwrittenfun.bukkit.lukkit.commandsender;

import org.bukkit.command.CommandSender;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.permissible.PermissibleObject;

public class CommandSenderObject extends LukkitObject {
	public CommandSender commandsender;

	public CommandSenderObject(CommandSender c) {
		commandsender = c;

		set("getName", new GetNameFunction());
		set("getServer", new GetServerFunction());
		set("sendMessage", new SendMessageFunction());
	}
	
	@Override
	public Object getObject() {
		return commandsender;
	}
}
