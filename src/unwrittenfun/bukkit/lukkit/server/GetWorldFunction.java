package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.world.WorldObject;


public class GetWorldFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new WorldObject(Bukkit.getServer().getWorld(args.tojstring(1)));
	}
	
}
