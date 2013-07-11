package unwrittenfun.bukkit.lukkit.world;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.player.PlayerObject;


public class GetPlayersFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		List<Player> players = ((World) ((LukkitObject) args.arg(1)).getObject()).getPlayers();
		LuaValue[] playerList = new LuaValue[players.size()];
		for (int i = 0; i < players.size(); i++) {
			playerList[i] = new PlayerObject(players.get(i));
		}
		return LuaValue.NIL;
	}
	
}
