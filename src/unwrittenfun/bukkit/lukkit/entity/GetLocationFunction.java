package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = ((Entity) ((LukkitObject) args.arg(1)).getObject()).getLocation();
		return LuaValue.varargsOf(new LuaValue[] {
				LuaValue.valueOf(loc.getX()),
				LuaValue.valueOf(loc.getY()),
				LuaValue.valueOf(loc.getZ()),
				LuaValue.valueOf(loc.getYaw()),
				LuaValue.valueOf(loc.getPitch())
		});
	}
	
}
