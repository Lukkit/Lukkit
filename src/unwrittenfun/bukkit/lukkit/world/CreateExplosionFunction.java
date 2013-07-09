package unwrittenfun.bukkit.lukkit.world;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class CreateExplosionFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		boolean rtn = ((WorldObject) args.arg(1)).world.createExplosion(args.todouble(2), 
																		args.todouble(3),
																		args.todouble(4),
																		args.tofloat(5),
																		args.toboolean(6),
																		args.toboolean(7));
		return LuaValue.valueOf(rtn);
	}
	
}
