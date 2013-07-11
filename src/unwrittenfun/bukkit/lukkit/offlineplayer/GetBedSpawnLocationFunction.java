package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetBedSpawnLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = ((OfflinePlayer) ((LukkitObject) args.arg(1)).getObject()).getBedSpawnLocation();
		return LuaValue.varargsOf(new LuaValue[]{
				LuaValue.valueOf(loc.getX()),
				LuaValue.valueOf(loc.getY()),
				LuaValue.valueOf(loc.getZ())
		});
	}
	
}
