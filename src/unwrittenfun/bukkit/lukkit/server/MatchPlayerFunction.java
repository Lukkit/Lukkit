package unwrittenfun.bukkit.lukkit.server;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import unwrittenfun.bukkit.lukkit.player.PlayerObject;


public class MatchPlayerFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		List<Player> matched = Bukkit.getServer().matchPlayer(args.tojstring(1));
		LuaValue[] matchedList = new LuaValue[matched.size()];
		for (int i = 0; i < matched.size(); i++) {
			matchedList[i] = LuaValue.valueOf(new PlayerObject(matched.get(i)));
		}
		return LuaValue.listOf(matchedList);
	}
	
}
