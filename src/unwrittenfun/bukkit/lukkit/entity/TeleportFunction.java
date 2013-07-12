package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class TeleportFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = new Location(((Entity) ((LukkitObject) args.arg(1)).getObject()).getWorld(), args.todouble(2), args.todouble(3), args.todouble(4));
		((Entity) ((LukkitObject) args.arg(1)).getObject()).teleport(loc);
		return LuaValue.NIL;
	}
	
}
