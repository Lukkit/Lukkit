package unwrittenfun.bukkit.lukkit.conversable;

import org.bukkit.conversations.Conversable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class AcceptConversationInputFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((Conversable) ((LukkitObject) args.arg(1)).getObject()).acceptConversationInput(args.tojstring(2));
		return LuaValue.NIL;
	}
	
}
