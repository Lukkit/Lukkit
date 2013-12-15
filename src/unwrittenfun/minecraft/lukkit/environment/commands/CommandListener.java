package unwrittenfun.minecraft.lukkit.environment.commands;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.Arrays;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class CommandListener implements Listener {
    public String name;
    public LuaFunction callback;

    public CommandListener(String _name, LuaFunction _callback) {
        this.name = _name;
        this.callback = _callback;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] jargs = event.getMessage().substring(1).split(" ");

        if (jargs[0].equalsIgnoreCase(name)) {
            jargs = Arrays.copyOfRange(jargs, 1, jargs.length);
            LuaValue[] largs = new LuaValue[jargs.length];
            for (int i = 0; i < largs.length; i++) {
                largs[i] = LuaValue.valueOf(jargs[i]);
            }
            LuaTable largsList = LuaValue.listOf(largs);

            event.setCancelled(true);
            callback.call(largsList, CoerceJavaToLua.coerce(event)).toboolean();
        }
    }
}
