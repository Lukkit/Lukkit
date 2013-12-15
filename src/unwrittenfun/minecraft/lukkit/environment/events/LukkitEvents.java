package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.Bukkit;
import org.luaj.vm2.LuaFunction;
import unwrittenfun.minecraft.lukkit.Lukkit;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitEvents {
    public static HashMap<String, ArrayList<LuaFunction>> eventMap = new HashMap<String, ArrayList<LuaFunction>>();

    public static void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new LukkitBlockEvents(), Lukkit.instance);
    }
}
