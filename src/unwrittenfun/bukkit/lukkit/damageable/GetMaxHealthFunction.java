package unwrittenfun.bukkit.lukkit.damageable;

import org.bukkit.entity.Damageable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetMaxHealthFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((Damageable) ((LukkitObject) args.arg(1)).getObject()).getMaxHealth());
	}
	
}
