package unwrittenfun.bukkit.lukkit.livingentity;

import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetCustomNameVisibleFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((LivingEntity) ((LukkitObject) args.arg(1)).getObject()).setCustomNameVisible(args.toboolean(2));
		return LuaValue.NIL;
	}
	
}
