package unwrittenfun.bukkit.lukkit;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public abstract class LukkitObject extends LuaTable {
	public abstract Object getObject();
	
	public void extendWith(LukkitObject obj) {
		LuaValue[] oKeys = obj.keys();
		
		for (int i = 0; i < oKeys.length; i++) {
			set(oKeys[i].tojstring(), obj.get(oKeys[i]));
		}
	}
}
