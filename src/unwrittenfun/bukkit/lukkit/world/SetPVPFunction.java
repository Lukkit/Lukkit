package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetPVPFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((World) ((LukkitObject) args.arg(1)).getObject()).setPVP(args.toboolean(2));
		return LuaValue.NIL;
	}
	
}
