package unwrittenfun.minecraft.lukkit.environment.wrappers;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import unwrittenfun.minecraft.lukkit.environment.LukkitCommand;
import unwrittenfun.minecraft.lukkit.environment.LukkitEnvironment;
import unwrittenfun.minecraft.lukkit.environment.LukkitPlugin;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
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

        set("writeFile", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                Writer writer = null;
                boolean append = args.optboolean(3, false);

                try {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(plugin.getDataFolder(), args.tojstring(1)), append), "utf-8"));
                    writer.write(args.tojstring(2));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        assert writer != null;
                        writer.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return LuaValue.NIL;
            }
        });

        set("readFile", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                try {
                    String contents = "";
                    List<String> lines = Files.readAllLines(new File(plugin.getDataFolder(), args.tojstring(1)).toPath(), Charset.forName("UTF-8"));
                    for (String line : lines) {
                        contents += line + "\n\r";
                    }
                    return LuaValue.valueOf(contents);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return LuaValue.NIL;
            }
        });

        set("config", new LukkitConfigWrapper(plugin));

        set("version", plugin.getDescription().getVersion());

        set("name", plugin.getName());

        set("path", plugin.getDataFolder().toString());
    }
}
