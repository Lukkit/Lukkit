package unwrittenfun.minecraft.lukkit.environment.wrappers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import unwrittenfun.minecraft.lukkit.environment.LukkitPlugin;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitConfigWrapper extends LuaTable {
    private LukkitPlugin plugin;

    public LukkitConfigWrapper(LukkitPlugin _plugin) {
        plugin = _plugin;

        set("save", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.saveConfig();
                return LuaValue.NIL;
            }
        });

        set("set", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                setLuaValue(args.tojstring(1), args.arg(2));
                return LuaValue.NIL;
            }
        });

        set("setDefault", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String path = args.tojstring(1);
                if (!plugin.getConfig().isSet(path))
                    setLuaValue(path, args.arg(2));
                return LuaValue.NIL;
            }
        });

        set("get", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                return getLuaValue(args.tojstring(1), args.arg(2));
            }
        });

        set("clear", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                plugin.getConfig().set(args.tojstring(1), null);
                return LuaValue.NIL;
            }
        });
    }

    private LuaValue getLuaValue(String path, LuaValue def) {
        FileConfiguration config = plugin.getConfig();
        if (config.isSet(path)) {
            if (config.isBoolean(path)) {
                return LuaValue.valueOf(config.getBoolean(path));
            } else if (config.isString(path)) {
                return LuaValue.valueOf(config.getString(path));
            } else if (config.isInt(path)) {
                return LuaValue.valueOf(config.getInt(path));
            } else if (config.isLong(path)) {
                return LuaValue.valueOf(config.getLong(path));
            } else if (config.isDouble(path)) {
                return LuaValue.valueOf(config.getDouble(path));
            }
        }

        return def;
    }

    private void setLuaValue(String path, LuaValue value) {
        if (value.isboolean()) {
            plugin.getConfig().set(path, value.toboolean());
        } else if (value.isint()) {
            plugin.getConfig().set(path, value.toint());
        } else if (value.isnumber()) {
            plugin.getConfig().set(path, value.todouble());
        } else if (value.isstring()) {
            plugin.getConfig().set(path, value.tojstring());
        } else {
            plugin.getConfig().set(path, value.toString());
        }
    }
}
