package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetSpawnLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = ((World) ((LukkitObject) args.arg(1)).getObject()).getSpawnLocation();
		return LuaValue.varargsOf(new LuaValue[] {
				LuaValue.valueOf(loc.getX()),
				LuaValue.valueOf(loc.getY()),
				LuaValue.valueOf(loc.getZ())
		});
	}
	
}
