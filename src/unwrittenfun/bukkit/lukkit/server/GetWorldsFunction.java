package unwrittenfun.bukkit.lukkit.server;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.world.WorldObject;


public class GetWorldsFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		List<World> worlds = Bukkit.getServer().getWorlds();
		LuaValue[] worldList = new LuaValue[worlds.size()];
		for (int i = 0; i < worlds.size(); i++) {
			worldList[i] = new WorldObject(worlds.get(i));
		}
		return LuaValue.varargsOf(worldList);
	}
	
}
