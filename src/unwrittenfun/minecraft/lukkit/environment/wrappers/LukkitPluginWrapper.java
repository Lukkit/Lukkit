package unwrittenfun.minecraft.lukkit.environment.wrappers;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import unwrittenfun.minecraft.lukkit.environment.LukkitPlugin;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitPluginWrapper extends LuaTable {
    private LukkitPlugin plugin;

    public LukkitPluginWrapper(LukkitPlugin _plugin) {
        plugin = _plugin;

        set("onEnable", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.enableCallback = args.checkfunction(1);
                return LuaValue.NIL;
            }
        });

        set("onDisable", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.disableCallback = args.checkfunction(1);
                return LuaValue.NIL;
            }
        });

        set("print", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.getLogger().info(args.tojstring(1));
                return LuaValue.NIL;
            }
        });

        set("warn", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.getLogger().warning(args.tojstring(1));
                return LuaValue.NIL;
            }
        });


        set("getVersion", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return LuaValue.valueOf(plugin.getDescription().getVersion());
            }
        });
    }
}
