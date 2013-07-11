package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.offlineplayer.OfflinePlayerObject;


public class GetOfflinePlayersFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		OfflinePlayer[] players = Bukkit.getServer().getOfflinePlayers();
		LuaValue[] playersList = new LuaValue[players.length];
		for (int i = 0; i < players.length; i++) {
			playersList[i] = new OfflinePlayerObject(players[i]);
		}
		return LuaValue.listOf(playersList);
	}
	
}
