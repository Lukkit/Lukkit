package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.ChatColor;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author jammehcow
 */

public class ChatColorWrapper extends LuaTable {

    private LukkitPlugin plugin;

    public ChatColorWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;


        set("getByChar", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(ChatColor.getByChar(c.checkjstring()));
            }
        });

        set("getLastColors", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(ChatColor.getLastColors(c.checkjstring()));
            }
        });

        set("stripColor", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(ChatColor.stripColor(c.checkjstring()));
            }
        });

        set("valueOf", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(ChatColor.valueOf(c.checkjstring()).toString());
            }
        });

        set("translateAlternateColorCodes", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue c, LuaValue s) {
                return CoerceJavaToLua.coerce(ChatColor.translateAlternateColorCodes(c.checkjstring().charAt(0), s.checkjstring()));
            }
        });


        for (ChatColor color : ChatColor.values())
            set(color.name(), CoerceJavaToLua.coerce(color.toString()));


    }

    @Override
    public String typename() {
        return ObjectType.WRAPPER.name;
    }

    @Override
    public int type() {
        return ObjectType.WRAPPER.type;
    }
}
