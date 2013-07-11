package unwrittenfun.bukkit.lukkit.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.player.PlayerObject;


public class GetOnlinePlayersFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		LuaValue[] playersList = new LuaValue[players.length];
		for (int i = 0; i < players.length; i++) {
			playersList[i] = new PlayerObject(players[i]);
		}
		return LuaValue.listOf(playersList);
	}
	
}
