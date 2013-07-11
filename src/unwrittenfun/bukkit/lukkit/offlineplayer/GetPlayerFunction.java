package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.bukkit.OfflinePlayer;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.player.PlayerObject;


public class GetPlayerFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new PlayerObject(((OfflinePlayer) ((LukkitObject) args.arg(1)).getObject()).getPlayer());
	}
	
}
