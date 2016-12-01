package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitBlockEvents implements Listener {
    public LukkitBlockEvents() {
        LukkitEvents.eventMap.put("blockBreak", new ArrayList<>());
        LukkitEvents.eventMap.put("blockBurn", new ArrayList<>());
        LukkitEvents.eventMap.put("blockCanBuild", new ArrayList<>());
        LukkitEvents.eventMap.put("blockDamage", new ArrayList<>());
        LukkitEvents.eventMap.put("blockDispense", new ArrayList<>());
        LukkitEvents.eventMap.put("blockExp", new ArrayList<>());
        LukkitEvents.eventMap.put("blockExplode", new ArrayList<>());
        LukkitEvents.eventMap.put("blockFade", new ArrayList<>());
        LukkitEvents.eventMap.put("blockForm", new ArrayList<>());
        LukkitEvents.eventMap.put("blockGrow", new ArrayList<>());
        LukkitEvents.eventMap.put("blockIgnite", new ArrayList<>());
        LukkitEvents.eventMap.put("blockMultiPlace", new ArrayList<>());
        LukkitEvents.eventMap.put("blockPhysics", new ArrayList<>());
        LukkitEvents.eventMap.put("blockPistonExtend", new ArrayList<>());
        LukkitEvents.eventMap.put("blockPistonRetract", new ArrayList<>());
        LukkitEvents.eventMap.put("blockPlace", new ArrayList<>());
        LukkitEvents.eventMap.put("blockRedstone", new ArrayList<>());
        LukkitEvents.eventMap.put("blockSpread", new ArrayList<>());
        LukkitEvents.eventMap.put("cauldronLevelChange", new ArrayList<>());
        LukkitEvents.eventMap.put("blockEntityForm", new ArrayList<>());
        LukkitEvents.eventMap.put("blockLeavesDecay", new ArrayList<>());
        LukkitEvents.eventMap.put("blockNotePlay", new ArrayList<>());
        LukkitEvents.eventMap.put("blockSignChange", new ArrayList<>());
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockBreak");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockBurn(BlockBurnEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockBurn");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockCanBuild(BlockCanBuildEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockCanBuild");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void blockDamage(BlockDamageEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockDamage");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockDispense(BlockDispenseEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockDispense");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockExp(BlockExpEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockExp");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void blockExplode(BlockExplodeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockExplode");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void blockFade(BlockFadeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockFade");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockForm(BlockFormEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockForm");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockGrow(BlockGrowEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockGrow");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockIgnite(BlockIgniteEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockIgnite");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockMultiPlace(BlockMultiPlaceEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockMultiPlace");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockPhysics(BlockPhysicsEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockPhysics");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockPistonExtend(BlockPistonExtendEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockPistonExtend");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockPistonRetract(BlockPistonRetractEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockPistonRetract");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockPlace");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockRedstone(BlockRedstoneEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockRedstone");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void blockSpread(BlockSpreadEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockSpread");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void cauldronLevelChange(CauldronLevelChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("cauldronLevelChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void blockEntityForm(EntityBlockFormEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockEntityForm");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockLeavesDecay(LeavesDecayEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockLeavesDecay");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockNotePlay(NotePlayEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockNotePlay");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void blockSignChange(SignChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("blockSignChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }
}
