package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SpawnFallingBlockFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = new Location(((World) ((LukkitObject) args.arg(1)).getObject()), args.todouble(2), args.todouble(3), args.todouble(4));
		((World) ((LukkitObject) args.arg(1)).getObject()).spawnFallingBlock(loc, args.toint(5), args.tobyte(6));
		return LuaValue.NIL;
	}
	
}
