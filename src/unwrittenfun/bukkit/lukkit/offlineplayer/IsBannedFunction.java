package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class IsBannedFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((OfflinePlayerObject) args.arg(1)).offlineplayer.isBanned());
	}
	
}
