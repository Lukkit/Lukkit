package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class StrikeLightningEffectFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = new Location(((World) ((LukkitObject) args.arg(1)).getObject()), args.todouble(2), args.todouble(3), args.todouble(4));
		((World) ((LukkitObject) args.arg(1)).getObject()).strikeLightningEffect(loc);
		return LuaValue.NIL;
	}
	
}
