package unwrittenfun.bukkit.lukkit.world;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class SetThunderingFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((WorldObject) args.arg(1)).world.setThundering(args.toboolean(2));
		return LuaValue.NIL;
	}
	
}
