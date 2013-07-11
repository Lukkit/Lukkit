package unwrittenfun.bukkit.lukkit.permissible;

import org.bukkit.permissions.Permissible;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class IsPermissionSetFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((Permissible) ((LukkitObject) args.arg(1)).getObject()).isPermissionSet(args.tojstring(2)));
	}
	
}
