package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class LoadChunkFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((World) ((LukkitObject) args.arg(1)).getObject()).loadChunk(args.toint(2), args.toint(3), args.toboolean(4));
		return LuaValue.NIL;
	}
	
}
