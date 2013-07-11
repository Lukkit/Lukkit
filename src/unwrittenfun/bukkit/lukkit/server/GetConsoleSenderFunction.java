package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.consolecommandsender.ConsoleCommandSenderObject;


public class GetConsoleSenderFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new ConsoleCommandSenderObject(Bukkit.getServer().getConsoleSender());
	}
	
}
