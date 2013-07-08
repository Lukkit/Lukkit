package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class SavePlayersFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Bukkit.getServer().savePlayers();
		return LuaValue.NIL;
	}
	
}
