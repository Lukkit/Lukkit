package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.ChatColor;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author jammehcow
 */

public class ChatColorWrapper extends LuaTable {
    public final ChatColor[] colors = {
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_RED,
            ChatColor.DARK_PURPLE,
            ChatColor.GOLD,
            ChatColor.GRAY,
            ChatColor.DARK_GRAY,
            ChatColor.BLUE,
            ChatColor.GREEN,
            ChatColor.AQUA,
            ChatColor.RED,
            ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW,
            ChatColor.WHITE,
            ChatColor.MAGIC,
            ChatColor.BOLD,
            ChatColor.STRIKETHROUGH,
            ChatColor.UNDERLINE,
            ChatColor.ITALIC,
            ChatColor.RESET
    };

    private LukkitPlugin plugin;

    public ChatColorWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;


        set("getByChar", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(ChatColor.getByChar(c.checkjstring()));
            }
        });

        for (ChatColor color : colors) {
            set(color.name().toLowerCase(), new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    return CoerceJavaToLua.coerce(color.toString());
                }
            });
        }


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
