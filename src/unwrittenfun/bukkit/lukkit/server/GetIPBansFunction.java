package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class GetIPBansFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		String[] bans = Bukkit.getServer().getIPBans().toArray(new String[0]);
		LuaValue[] bansList = new LuaValue[bans.length];
		
		for (int i = 0; i < bans.length; i++) {
			bansList[i] = LuaValue.valueOf(bans[i]);
		}
		
		return LuaValue.listOf(bansList);
	}
	
}
