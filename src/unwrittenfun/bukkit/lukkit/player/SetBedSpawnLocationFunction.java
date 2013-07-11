package unwrittenfun.bukkit.lukkit.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;

public class SetBedSpawnLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = new Location(((Player) ((LukkitObject) args.arg(1)).getObject()).getWorld(), args.todouble(2), args.todouble(3), args.todouble(4));
		((Player) ((LukkitObject) args.arg(1)).getObject()).setBedSpawnLocation(loc);
		return LuaValue.NIL;
	}
	
	
}
