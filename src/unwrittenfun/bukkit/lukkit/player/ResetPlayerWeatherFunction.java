package unwrittenfun.bukkit.lukkit.player;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class ResetPlayerWeatherFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((Player) ((LukkitObject) args.arg(1)).getObject()).resetPlayerWeather();
		return LuaValue.NIL;
	}
	
}
