package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class DispatchCommandFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		CommandSender commandSender = ((CommandSender) ((LukkitObject) args.arg(1)).getObject());
		return LuaValue.valueOf(Bukkit.getServer().dispatchCommand(commandSender, args.tojstring(2)));
	}
	
}
