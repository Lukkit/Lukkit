package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import unwrittenfun.minecraft.lukkit.environment.wrappers.LukkitPluginWrapper;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitObject extends LuaTable {

    public LukkitObject() {

        set("addCommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                LukkitEnvironment.registerCommand(new LukkitCommand(args.tojstring(1), args.tojstring(2), args.tojstring(3), new ArrayList<String>(), args.checkfunction(4)));
                return LuaValue.NIL;
            }
        });

        set("async", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                LukkitAsyncThread thread = new LukkitAsyncThread(args.tojstring(1), args.checkfunction(2));
                thread.start();
                return LuaValue.NIL;
            }
        });

        set("addPlugin", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                LukkitPlugin plugin = new LukkitPlugin(LukkitEnvironment.pluginLoader, args.tojstring(1), args.tojstring(2));
                LukkitPluginWrapper wrapper = new LukkitPluginWrapper(plugin);
                if (!args.isnoneornil(3))
                    args.checkfunction(3).call(wrapper);
                plugin.getPluginLoader().enablePlugin(plugin);
                return wrapper;
            }
        });
    }
}
