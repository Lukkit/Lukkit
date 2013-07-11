package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.offlineplayer.OfflinePlayerObject;


public class GetOfflinePlayerFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new OfflinePlayerObject(Bukkit.getServer().getOfflinePlayer(args.tojstring(1)));
	}
	
}
