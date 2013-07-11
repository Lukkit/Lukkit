package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetTimeFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((World) ((LukkitObject) args.arg(1)).getObject()).setTime(args.tolong(2));
		return LuaValue.NIL;
	}
	
}
