package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class CreateExplosionFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		boolean rtn = ((World) ((LukkitObject) args.arg(1)).getObject()).createExplosion(args.todouble(2), 
																		args.todouble(3),
																		args.todouble(4),
																		args.tofloat(5),
																		args.toboolean(6),
																		args.toboolean(7));
		return LuaValue.valueOf(rtn);
	}
	
}
