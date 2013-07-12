package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetVelocityFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Vector vel = ((Entity) ((LukkitObject) args.arg(1)).getObject()).getVelocity();
		return LuaValue.varargsOf(new LuaValue[] {
				LuaValue.valueOf(vel.getX()),
				LuaValue.valueOf(vel.getY()),
				LuaValue.valueOf(vel.getZ())
		});
	}
	
}
