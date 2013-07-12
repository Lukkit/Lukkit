package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetTicksLivedFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((Entity) ((LukkitObject) args.arg(1)).getObject()).setTicksLived(args.toint(2));
		return LuaValue.NIL;
	}
	
}
