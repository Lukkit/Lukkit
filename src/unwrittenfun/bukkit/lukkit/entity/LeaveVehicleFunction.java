package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class LeaveVehicleFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.valueOf(((Entity) ((LukkitObject) args.arg(1)).getObject()).leaveVehicle());
	}
	
}
