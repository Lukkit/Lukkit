package unwrittenfun.minecraft.lukkit.environment;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import unwrittenfun.minecraft.lukkit.Lukkit;
import unwrittenfun.minecraft.lukkit.environment.json.LukkitJsonParser;
import unwrittenfun.minecraft.lukkit.environment.wrappers.LukkitPluginWrapper;

import java.io.File;
import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
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

        set("include", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (args.narg() == 2) {
                    String affix = "";
                    if (!args.tojstring(2).endsWith(".lua")) affix = ".lua";
                    File includeFile = new File(new File(Lukkit.instance.getDataFolder(), args.tojstring(1)), args.tojstring(2) + affix);
                    if (includeFile.exists()) {
                        LukkitEnvironment.loadFile(includeFile);
                        return LuaValue.TRUE;
                    } else {
                        Lukkit.logger.warning("Could not find file to include - " + includeFile.getName());
                    }
                }
                return LuaValue.FALSE;
            }
        });

        set("toJSON", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return LuaValue.valueOf(LukkitJsonParser.parseToJSON(args.checktable(1)));
            }
        });
    }
}
