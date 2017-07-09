package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class UtilitiesWrapper extends LuaTable {
    private LukkitPlugin plugin;

    public UtilitiesWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;

        set("broadcast", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                plugin.getServer().broadcastMessage(arg.checkjstring());
                return LuaValue.NIL;
            }
        });

        set("getTableFromList", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                Object[] list;

                if (arg.checkuserdata() instanceof Collection) {
                    list = ((Collection<?>) arg.touserdata()).toArray();
                } else if (arg.touserdata() instanceof Stream) {
                    list = ((Stream<?>) arg.touserdata()).toArray();
                } else {
                    throw new LuaError("utils.tableFromList(obj) was passed something other than an instance of Collection or Stream.");
                }

                LuaTable t = new LuaTable();
                for (int i = 0; i < list.length; i++) {
                    t.set(LuaValue.valueOf(i + 1), ConfigWrapper.castObject(list[i]));
                }

                return t;
            }
        });

        set("getTableLength", new OneArgFunction() {
            // Useful when you have a table with set keys (like strings) and you want to get the size of it. Using # will return 0.
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaValue.valueOf(arg.checktable().keyCount());
            }
        });
    }
}
