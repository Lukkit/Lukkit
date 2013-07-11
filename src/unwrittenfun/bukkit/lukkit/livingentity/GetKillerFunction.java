package unwrittenfun.bukkit.lukkit.livingentity;

import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.player.PlayerObject;


public class GetKillerFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return new PlayerObject(((LivingEntity) ((LukkitObject) args.arg(1)).getObject()).getKiller());
	}
	
}
