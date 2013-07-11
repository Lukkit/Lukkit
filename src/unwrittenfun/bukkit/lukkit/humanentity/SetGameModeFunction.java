package unwrittenfun.bukkit.lukkit.humanentity;

import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class SetGameModeFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((HumanEntity) ((LukkitObject) args.arg(1)).getObject()).setGameMode(GameMode.getByValue(args.toint(2)));
		return LuaValue.NIL;
	}
	
}
