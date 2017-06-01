package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 * @author jammehcow
 */

public class ConfigWrapper extends LuaTable {
    private LukkitPlugin plugin;

    public ConfigWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;

        set("getValue", new OneArgFunction() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public LuaValue call(LuaValue key) {
                return castObject(plugin.getConfig().get(key.checkjstring()));
            }
        });

        set("setDefault", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue path, LuaValue value) {
                if (!plugin.getConfig().contains(path.checkjstring())) {
                    // Checking the type of LuaValue we received and get the Java-related object
                    if (value instanceof LuaString) {
                        plugin.getConfig().set(path.tojstring(), value.tojstring());
                    } else if (value instanceof LuaInteger) {
                        plugin.getConfig().set(path.tojstring(), value.toint());
                    } else if (value instanceof LuaDouble) {
                        plugin.getConfig().set(path.tojstring(), value.todouble());
                    } else if (value instanceof LuaBoolean) {
                        plugin.getConfig().set(path.tojstring(), value.toboolean());
                    } else {
                        // TODO
                        plugin.getLogger().warning("A value was passed to config.setDefalt() which wasn't of a supported type.");
                    }
                }
                // Rather than making the developer save the config we'll do it automatically
                plugin.saveConfig();
                return LuaValue.NIL;
            }
        });

        set("set", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue path, LuaValue value) {
                if (value instanceof LuaString) {
                    plugin.getConfig().set(path.tojstring(), value.tojstring());
                } else if (value instanceof LuaInteger) {
                    plugin.getConfig().set(path.tojstring(), value.toint());
                } else if (value instanceof LuaDouble) {
                    plugin.getConfig().set(path.tojstring(), value.todouble());
                } else if (value instanceof LuaBoolean) {
                    plugin.getConfig().set(path.tojstring(), value.toboolean());
                } else {
                    // TODO
                    plugin.getLogger().warning("A value was passed to config.set() which wasn't of a supported type.");
                }
                plugin.saveConfig();
                return LuaValue.NIL;
            }
        });

        set("clear", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue path) {
                plugin.getConfig().set(path.checkjstring(), null);
                plugin.saveConfig();
                return LuaValue.NIL;
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private LuaValue castObject(Object obj) {
        if (obj instanceof String) {
            return LuaValue.valueOf((String) obj);
        } else if (obj instanceof Integer) {
            return LuaValue.valueOf((int) obj);
        } else if (obj instanceof Double) {
            return LuaValue.valueOf(((double) obj));
        } else if (obj instanceof Boolean) {
            return LuaValue.valueOf(((boolean) obj));
        } else {
            return LuaValue.NIL;
        }
    }
}