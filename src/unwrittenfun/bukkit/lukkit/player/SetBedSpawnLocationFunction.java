package unwrittenfun.bukkit.lukkit.player;

import org.bukkit.Location;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class SetBedSpawnLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = new Location(((PlayerObject) args.arg(1)).player.getWorld(), args.todouble(2), args.todouble(3), args.todouble(4));
		((PlayerObject) args.arg(1)).player.setBedSpawnLocation(loc);
		return LuaValue.NIL;
	}
	
	
}
