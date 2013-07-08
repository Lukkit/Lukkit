package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class UnbanIPFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Bukkit.getServer().unbanIP(args.tojstring(1));
		return LuaValue.NIL;
	}
	
}
