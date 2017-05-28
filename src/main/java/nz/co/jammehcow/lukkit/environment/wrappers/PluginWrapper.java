package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 * @author jammehcow
 */

public class PluginWrapper extends LuaTable {
    private LukkitPlugin plugin;

    public PluginWrapper(final LukkitPlugin plugin) {
        this.plugin = plugin;

        // Would rather wrap the entire plugin manually than just coerce it. Make it Lua friendly.

        set("getName", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(plugin.getName());
            }
        });

        set("getVersion", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(plugin.getDescription().getVersion());
            }
        });
    }
}
