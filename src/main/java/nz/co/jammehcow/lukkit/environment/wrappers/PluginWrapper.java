package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * The type Plugin wrapper.
 *
 * @author jammehcow
 */
public class PluginWrapper extends LuaTable {
    private LukkitPlugin plugin;

    /**
     * Creates a new wrapper for the plugin to interact with.
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
                    Main.instance.getLogger().warning("The plugin " + plugin.getName() + " tried to add an onLoad callback but provided a " + callback.typename() + " instead of a function.");
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
                    Main.instance.getLogger().warning("The plugin " + plugin.getName() + " tried to add an onEnable callback but provided a " + callback.typename() + " instead of a function.");
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
                    Main.instance.getLogger().warning("The plugin " + plugin.getName() + " tried to add an onDisable callback but provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });
    }
}
