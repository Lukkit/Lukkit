package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class GetBedSpawnLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = ((OfflinePlayerObject) args.arg(1)).offlineplayer.getBedSpawnLocation();
		return LuaValue.varargsOf(new LuaValue[]{
				LuaValue.valueOf(loc.getX()),
				LuaValue.valueOf(loc.getY()),
				LuaValue.valueOf(loc.getZ())
		});
	}
	
}
