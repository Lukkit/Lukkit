package unwrittenfun.bukkit.lukkit.permissible;

import org.bukkit.permissions.Permissible;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.Lukkit;
import unwrittenfun.bukkit.lukkit.LukkitObject;


public class AddAttachmentFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((Permissible) ((LukkitObject) args.arg(1)).getObject()).addAttachment(Lukkit.plugin, args.tojstring(2), args.toboolean(3));
		return LuaValue.NIL;
	}
	
}
