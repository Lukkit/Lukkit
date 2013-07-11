package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetWaterAnimalSpawnLimitFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((World) ((LukkitObject) args.arg(1)).getObject()).getWaterAnimalSpawnLimit());
	}
	
}
