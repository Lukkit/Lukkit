package unwrittenfun.minecraft.lukkit.environment.wrappers;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import unwrittenfun.minecraft.lukkit.environment.LukkitCommand;
import unwrittenfun.minecraft.lukkit.environment.LukkitEnvironment;
import unwrittenfun.minecraft.lukkit.environment.LukkitPlugin;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitPluginWrapper extends LuaTable {
    private LukkitPlugin plugin;

    public LukkitPluginWrapper(LukkitPlugin _plugin) {
        plugin = _plugin;

        set("addCommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                LukkitEnvironment.registerCommand(new LukkitCommand(args.tojstring(1), args.tojstring(2), args.tojstring(3), new ArrayList<String>(), args.checkfunction(4)));
                return LuaValue.NIL;
            }
        });

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

        set("version", plugin.getDescription().getVersion());
    }
}
