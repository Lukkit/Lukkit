package unwrittenfun.bukkit.lukkit.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetBedSpawnLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Player player = ((Player) ((LukkitObject) args.arg(1)).getObject());
		Location loc = player.getBedSpawnLocation();
		return LuaValue.varargsOf(new LuaValue[]{
				LuaValue.valueOf(loc.getX()),
				LuaValue.valueOf(loc.getY()),
				LuaValue.valueOf(loc.getZ())
		});
	}
	
}
