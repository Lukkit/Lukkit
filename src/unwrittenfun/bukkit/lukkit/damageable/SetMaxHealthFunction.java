package unwrittenfun.bukkit.lukkit.damageable;

import org.bukkit.entity.Damageable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetMaxHealthFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((Damageable) ((LukkitObject) args.arg(1)).getObject()).setMaxHealth(args.todouble(2));
		return LuaValue.NIL;
	}
	
}
