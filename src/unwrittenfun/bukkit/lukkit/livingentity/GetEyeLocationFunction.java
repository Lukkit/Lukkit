package unwrittenfun.bukkit.lukkit.livingentity;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetEyeLocationFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Location loc = ((LivingEntity) ((LukkitObject) args.arg(1)).getObject()).getEyeLocation();
		return LuaValue.varargsOf(new LuaValue[] {
				LuaValue.valueOf(loc.getX()),
				LuaValue.valueOf(loc.getY()),
				LuaValue.valueOf(loc.getZ())
		});
	}
	
}
