package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.entity.Entity;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.world.WorldObject;


public class GetWorldFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new WorldObject(((Entity) ((LukkitObject) args.arg(1)).getObject()).getWorld());
	}
	
}
