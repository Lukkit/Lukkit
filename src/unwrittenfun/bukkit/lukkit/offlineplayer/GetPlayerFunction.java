package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.player.PlayerObject;


public class GetPlayerFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new PlayerObject(((OfflinePlayerObject) args.arg(1)).offlineplayer.getPlayer());
	}
	
}
