package unwrittenfun.bukkit.lukkit.commandsender;

import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.server.ServerObject;


public class GetServerFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new ServerObject();
	}
	
}
