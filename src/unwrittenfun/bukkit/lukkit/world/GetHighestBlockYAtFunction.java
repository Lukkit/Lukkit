package unwrittenfun.bukkit.lukkit.world;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class GetHighestBlockYAtFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((WorldObject) args.arg(1)).world.getHighestBlockYAt(args.toint(2), args.toint(3)));
	}
	
}
