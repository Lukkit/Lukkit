package unwrittenfun.bukkit.lukkit.humanentity;

import org.bukkit.entity.HumanEntity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class CloseInventoryFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		((HumanEntity) ((LukkitObject) args.arg(1)).getObject()).closeInventory();
		return LuaValue.NIL;
	}
	
}
