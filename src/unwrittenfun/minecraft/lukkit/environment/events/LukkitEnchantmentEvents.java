
package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitEnchantmentEvents implements Listener {
    public LukkitEnchantmentEvents() {
        LukkitEvents.eventMap.put("enchantItem", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("prepareItemEnchant", new ArrayList<LuaFunction>());
    }

    @EventHandler
    public void enchantItem(EnchantItemEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("enchantItem");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void prepareItemEnchant(PrepareItemEnchantEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("prepareItemEnchant");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }
}