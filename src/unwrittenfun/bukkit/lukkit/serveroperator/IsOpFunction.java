package unwrittenfun.bukkit.lukkit.serveroperator;

import org.bukkit.permissions.ServerOperator;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class IsOpFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((ServerOperator) ((LukkitObject) args.arg(1)).getObject()).isOp());
	}
	
}
