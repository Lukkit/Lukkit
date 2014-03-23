
package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitWeatherEvents implements Listener {
    public LukkitWeatherEvents() {
        LukkitEvents.eventMap.put("lightningStrike", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("thunderChange", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("weatherChange", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("weather", new ArrayList<LuaFunction>());
    }

    @EventHandler
    public void lightningStrike(LightningStrikeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("lightningStrike");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void thunderChange(ThunderChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("thunderChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void weatherChange(WeatherChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("weatherChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void weather(WeatherEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("weather");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }
}