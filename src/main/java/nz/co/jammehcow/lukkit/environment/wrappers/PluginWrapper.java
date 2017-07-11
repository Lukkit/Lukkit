package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * The plugin wrapper providing access to plugin functions.
 *
 * @author jammehcow
 */
public class PluginWrapper extends LuaTable {
    private LukkitPlugin plugin;

    /**
     * Creates a new plugin wrapper.
     *
     * @param plugin the Lukkit plugin
     */
    public PluginWrapper(final LukkitPlugin plugin) {
        this.plugin = plugin;

        set("onLoad", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setLoadCB(callback.checkfunction());
                } else {
                    plugin.getLogger().warning("I tried to add an onLoad callback but provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("onEnable", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setEnableCB(callback.checkfunction());
                } else {
                    plugin.getLogger().warning("I tried to add an onEnable callback but provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("onDisable", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setDisableCB(callback.checkfunction());
                } else {
                    plugin.getLogger().warning("I tried to add an onDisable callback but provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("addCommand", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                if (!arg1.isstring() || !arg2.isfunction()) {
                    // TODO
                    plugin.getLogger().severe("I tried to register a command but there was an issue doing so. Check that the command registration conforms to the layout here: ");
                } else {
                    plugin.addCommand(arg1.checkjstring(), arg2.checkfunction());
                }
                return LuaValue.NIL;
            }
        });

        set("getServer", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(plugin.getServer());
            }
        });

        set("setNaggable", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                plugin.setNaggable(arg.checkboolean());
                return LuaValue.NIL;
            }
        });

        set("isNaggable", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(plugin.isNaggable());
            }
        });
    }
}
