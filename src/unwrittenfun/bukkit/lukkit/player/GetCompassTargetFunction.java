package unwrittenfun.bukkit.lukkit.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class GetCompassTargetFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Player player = ((PlayerObject) args.arg(1)).player;
		Location loc = player.getLocation();
		return LuaValue.varargsOf(new LuaValue[]{
				LuaValue.valueOf(loc.getX()),
				LuaValue.valueOf(loc.getY()),
				LuaValue.valueOf(loc.getZ()),
				LuaValue.valueOf(loc.getYaw()),
				LuaValue.valueOf(loc.getPitch())
		});
	}
	
}
