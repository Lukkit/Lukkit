package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 * @author jammehcow
 */

public class ConfigWrapper extends LuaTable {
    private LukkitPlugin plugin;
    private boolean autosave;

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
                        plugin.getConfig().set(path.tojstring(), value.touserdata());
                    }
                } else {
                    return LuaValue.FALSE;
                }
                // Rather than making the developer save the config we'll do it automatically. Can be disabled by config.setAutosave(false)
                if (autosave) plugin.saveConfig();
                return LuaValue.TRUE;
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
                    plugin.getConfig().set(path.tojstring(), value.touserdata());
                }
                if (autosave) plugin.saveConfig();
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

        set("setAutosave", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                autosave = arg.checkboolean();
                return LuaValue.NIL;
            }
        });

        set("save", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                plugin.saveConfig();
                return LuaValue.NIL;
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    static LuaValue castObject(Object obj) {
        if (obj instanceof String) {
            return LuaValue.valueOf((String) obj);
        } else if (obj instanceof Integer) {
            return LuaValue.valueOf((int) obj);
        } else if (obj instanceof Double) {
            return LuaValue.valueOf(((double) obj));
        } else if (obj instanceof Boolean) {
            return LuaValue.valueOf(((boolean) obj));
        } else {
            return LuaValue.userdataOf(obj);
        }
    }
}