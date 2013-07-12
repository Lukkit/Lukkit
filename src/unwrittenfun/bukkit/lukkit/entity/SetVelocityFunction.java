package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetVelocityFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Vector vel = new Vector(args.todouble(2), args.todouble(3), args.todouble(4));
		((Entity) ((LukkitObject) args.arg(1)).getObject()).setVelocity(vel);
		return LuaValue.NIL;
	}
	
}
