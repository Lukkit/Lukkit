package unwrittenfun.bukkit.lukkit.conversable;

import org.bukkit.conversations.Conversable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class IsConversingFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((Conversable) ((LukkitObject) args.arg(1)).getObject()).isConversing());
	}
	
}
