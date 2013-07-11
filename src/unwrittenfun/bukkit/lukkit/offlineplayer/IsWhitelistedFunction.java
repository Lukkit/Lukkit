package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.bukkit.OfflinePlayer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class IsWhitelistedFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((OfflinePlayer) ((LukkitObject) args.arg(1)).getObject()).isWhitelisted());
	}
	
}
