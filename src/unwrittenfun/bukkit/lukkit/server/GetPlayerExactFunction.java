package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.player.PlayerObject;


public class GetPlayerExactFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new PlayerObject(Bukkit.getServer().getPlayer(args.tojstring(1)));
	}
	
}
