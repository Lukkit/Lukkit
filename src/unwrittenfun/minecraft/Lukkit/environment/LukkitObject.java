package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.LuaTable;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitObject extends LuaTable {
    public LukkitObject(ILukkitObject lukkitObject) {
        String[] methods = lukkitObject.getMethods();
        for (int i = 0; i < methods.length; i++) {
            set(methods[i], new LukkitMethod(i, methods[i], lukkitObject));
        }
    }
}
