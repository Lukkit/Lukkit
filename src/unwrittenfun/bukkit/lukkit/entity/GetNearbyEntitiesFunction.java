package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetNearbyEntitiesFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		Entity[] entities = ((Entity) ((LukkitObject) args.arg(1)).getObject()).getNearbyEntities(args.todouble(2), args.todouble(3), args.todouble(4)).toArray(new Entity[0]);
		LuaValue[] entityList = new LuaValue[entities.length];
		for (int i = 0; i < entities.length; i++) {
			entityList[i] = new EntityObject(entities[i]);
		}
		return LuaValue.NIL;
	}
	
}
