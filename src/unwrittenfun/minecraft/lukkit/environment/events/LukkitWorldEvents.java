
package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitWorldEvents implements Listener {
    public LukkitWorldEvents() {
        LukkitEvents.eventMap.put("chunkLoad", new ArrayList<>());
        LukkitEvents.eventMap.put("chunkPopulate", new ArrayList<>());
        LukkitEvents.eventMap.put("chunkUnload", new ArrayList<>());
        LukkitEvents.eventMap.put("portalCreate", new ArrayList<>());
        LukkitEvents.eventMap.put("spawnChange", new ArrayList<>());
        LukkitEvents.eventMap.put("structureGrow", new ArrayList<>());
        LukkitEvents.eventMap.put("worldInit", new ArrayList<>());
        LukkitEvents.eventMap.put("worldLoad", new ArrayList<>());
        LukkitEvents.eventMap.put("worldSave", new ArrayList<>());
        LukkitEvents.eventMap.put("worldUnload", new ArrayList<>());
    }

    @EventHandler
    public void chunkLoad(ChunkLoadEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("chunkLoad");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void chunkPopulate(ChunkPopulateEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("chunkPopulate");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void chunkUnload(ChunkUnloadEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("chunkUnload");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void portalCreate(PortalCreateEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("portalCreate");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void spawnChange(SpawnChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("spawnChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void structureGrow(StructureGrowEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("structureGrow");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void worldInit(WorldInitEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("worldInit");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void worldLoad(WorldLoadEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("worldLoad");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void worldSave(WorldSaveEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("worldSave");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void worldUnload(WorldUnloadEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("worldUnload");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }
}