package unwrittenfun.bukkit.lukkit.world;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class SetPVPFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((WorldObject) args.arg(1)).world.setPVP(args.toboolean(2));
		return LuaValue.NIL;
	}
	
}
