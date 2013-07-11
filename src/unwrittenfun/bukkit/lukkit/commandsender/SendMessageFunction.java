package unwrittenfun.bukkit.lukkit.commandsender;

import org.bukkit.command.CommandSender;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SendMessageFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((CommandSender) ((LukkitObject) args.arg(1)).getObject()).sendMessage(args.tojstring(2));
		return LuaValue.NIL;
	}
	
}
