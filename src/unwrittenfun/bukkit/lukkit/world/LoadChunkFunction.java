package unwrittenfun.bukkit.lukkit.world;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class LoadChunkFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((WorldObject) args.arg(1)).world.loadChunk(args.toint(2), args.toint(3), args.toboolean(4));
		return LuaValue.NIL;
	}
	
}
