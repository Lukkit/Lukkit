package unwrittenfun.minecraft.lukkit.environment.events;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitEventObject extends LuaTable {
    public LukkitEventObject() {
        this.set("add", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                if (arg2.isfunction() && LukkitEvents.eventMap.containsKey(arg1.tojstring())) {
                    LukkitEvents.eventMap.get(arg1.tojstring()).add((LuaFunction) arg2);
                    return LuaValue.TRUE;
                }
                return LuaValue.FALSE;
            }
        });
    }
}
