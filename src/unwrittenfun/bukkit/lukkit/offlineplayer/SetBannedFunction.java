package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.bukkit.OfflinePlayer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetBannedFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((OfflinePlayer) ((LukkitObject) args.arg(1)).getObject()).setBanned(args.toboolean(2));
		return LuaValue.NIL;
	}
	
}
