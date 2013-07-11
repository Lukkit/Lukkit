package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class UnloadWordFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(Bukkit.getServer().unloadWorld(((World) ((LukkitObject) args.arg(1)).getObject()), args.toboolean(2)));
	}
	
}
