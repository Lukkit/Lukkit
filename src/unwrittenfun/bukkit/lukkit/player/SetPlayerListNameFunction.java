package unwrittenfun.bukkit.lukkit.player;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetPlayerListNameFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((Player) ((LukkitObject) args.arg(1)).getObject()).setPlayerListName(args.tojstring(2));
		return LuaValue.NIL;
	}
	
}
