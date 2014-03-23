
package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitServerEvents implements Listener {
    public LukkitServerEvents() {
        LukkitEvents.eventMap.put("mapInitialize", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("pluginDisable", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("pluginEnable", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("plugin", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("remoteServerCommand", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("serverCommand", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("server", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("serverListPing", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("service", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("serviceRegister", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("serviceUnregister", new ArrayList<LuaFunction>());
    }

    @EventHandler
    public void mapInitialize(MapInitializeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("mapInitialize");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void pluginDisable(PluginDisableEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("pluginDisable");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void pluginEnable(PluginEnableEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("pluginEnable");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void plugin(PluginEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("plugin");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void remoteServerCommand(RemoteServerCommandEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("remoteServerCommand");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void serverCommand(ServerCommandEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("serverCommand");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void server(ServerEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("server");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void serverListPing(ServerListPingEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("serverListPing");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void service(ServiceEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("service");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void serviceRegister(ServiceRegisterEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("serviceRegister");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void serviceUnregister(ServiceUnregisterEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("serviceUnregister");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }
}