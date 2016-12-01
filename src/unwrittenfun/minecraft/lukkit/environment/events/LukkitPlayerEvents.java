
package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitPlayerEvents implements Listener {
    public LukkitPlayerEvents() {
        LukkitEvents.eventMap.put("asyncPlayerChat", new ArrayList<>());
        LukkitEvents.eventMap.put("asyncPlayerPreLogin", new ArrayList<>());
        LukkitEvents.eventMap.put("playerAnimation", new ArrayList<>());
        LukkitEvents.eventMap.put("playerArmorStandManipulate", new ArrayList<>());
        LukkitEvents.eventMap.put("playerBedEnter", new ArrayList<>());
        LukkitEvents.eventMap.put("playerBedLeave", new ArrayList<>());
        LukkitEvents.eventMap.put("playerBucketEmpty", new ArrayList<>());
        LukkitEvents.eventMap.put("playerBucketFill", new ArrayList<>());
        LukkitEvents.eventMap.put("playerChangedMainHand", new ArrayList<>());
        LukkitEvents.eventMap.put("playerChangedWorld", new ArrayList<>());
        LukkitEvents.eventMap.put("playerChannel", new ArrayList<>());
        LukkitEvents.eventMap.put("playerChatTabComplete", new ArrayList<>());
        LukkitEvents.eventMap.put("playerCommandPreprocess", new ArrayList<>());
        LukkitEvents.eventMap.put("playerDropItem", new ArrayList<>());
        LukkitEvents.eventMap.put("playerEditBook", new ArrayList<>());
        LukkitEvents.eventMap.put("playerEggThrow", new ArrayList<>());
        LukkitEvents.eventMap.put("playerExpChange", new ArrayList<>());
        LukkitEvents.eventMap.put("playerFish", new ArrayList<>());
        LukkitEvents.eventMap.put("playerGameModeChange", new ArrayList<>());
        LukkitEvents.eventMap.put("playerInteractAtEntity", new ArrayList<>());
        LukkitEvents.eventMap.put("playerInteractEntity", new ArrayList<>());
        LukkitEvents.eventMap.put("playerInteract", new ArrayList<>());
        LukkitEvents.eventMap.put("playerItemBreak", new ArrayList<>());
        LukkitEvents.eventMap.put("playerItemConsume", new ArrayList<>());
        LukkitEvents.eventMap.put("playerItemDamage", new ArrayList<>());
        LukkitEvents.eventMap.put("playerItemHeld", new ArrayList<>());
        LukkitEvents.eventMap.put("playerJoin", new ArrayList<>());
        LukkitEvents.eventMap.put("playerKick", new ArrayList<>());
        LukkitEvents.eventMap.put("playerLevelChange", new ArrayList<>());
        LukkitEvents.eventMap.put("playerLogin", new ArrayList<>());
        LukkitEvents.eventMap.put("playerMove", new ArrayList<>());
        LukkitEvents.eventMap.put("playerPickupArrow", new ArrayList<>());
        LukkitEvents.eventMap.put("playerPickupItem", new ArrayList<>());
        LukkitEvents.eventMap.put("playerPortal", new ArrayList<>());
        LukkitEvents.eventMap.put("playerQuit", new ArrayList<>());
        LukkitEvents.eventMap.put("playerRegisterChannel", new ArrayList<>());
        LukkitEvents.eventMap.put("playerRespawn", new ArrayList<>());
        LukkitEvents.eventMap.put("playerShearEntity", new ArrayList<>());
        LukkitEvents.eventMap.put("playerStatisticIncrement", new ArrayList<>());
        LukkitEvents.eventMap.put("playerSwapHandItems", new ArrayList<>());
        LukkitEvents.eventMap.put("playerTeleport", new ArrayList<>());
        LukkitEvents.eventMap.put("playerToggleFlight", new ArrayList<>());
        LukkitEvents.eventMap.put("playerToggleSneak", new ArrayList<>());
        LukkitEvents.eventMap.put("playerToggleSprint", new ArrayList<>());
        LukkitEvents.eventMap.put("playerUnleashEntity", new ArrayList<>());
        LukkitEvents.eventMap.put("playerUnregisterChannel", new ArrayList<>());
        LukkitEvents.eventMap.put("playerVelocity", new ArrayList<>());
    }

    @EventHandler
    public void asyncPlayerChat(AsyncPlayerChatEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("asyncPlayerChat");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("asyncPlayerPreLogin");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerAnimation(PlayerAnimationEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerAnimation");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerArmorStandManipulate");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerBedEnter(PlayerBedEnterEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerBedEnter");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerBedLeave(PlayerBedLeaveEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerBedLeave");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerBucketEmpty(PlayerBucketEmptyEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerBucketEmpty");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerBucketFill(PlayerBucketFillEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerBucketFill");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerChangedMainHand(PlayerChangedMainHandEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerChangedMainHand");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerChangedWorld(PlayerChangedWorldEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerChangedWorld");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerChannel(PlayerChannelEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerChannel");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerChatTabComplete(PlayerChatTabCompleteEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerChatTabComplete");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerCommandPreprocess");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerDropItem");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerEditBook(PlayerEditBookEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerEditBook");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerEggThrow(PlayerEggThrowEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerEggThrow");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerExpChange(PlayerExpChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerExpChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerFish(PlayerFishEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerFish");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerGameModeChange(PlayerGameModeChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerGameModeChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerInteractAtEntity");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerInteractEntity(PlayerInteractEntityEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerInteractEntity");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerInteract");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerItemBreak(PlayerItemBreakEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerItemBreak");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerItemConsume(PlayerItemConsumeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerItemConsume");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerItemDamage(PlayerItemDamageEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerItemDamage");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerItemHeld(PlayerItemHeldEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerItemHeld");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerJoin");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerKick(PlayerKickEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerKick");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerLevelChange(PlayerLevelChangeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerLevelChange");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerLogin(PlayerLoginEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerLogin");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerMove");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerPickupArrow(PlayerPickupArrowEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerPickupArrow");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerPickupItem(PlayerPickupItemEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerPickupItem");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerPortal(PlayerPortalEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerPortal");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerQuit");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerRegisterChannel(PlayerRegisterChannelEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerRegisterChannel");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerRespawn");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerShearEntity(PlayerShearEntityEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerShearEntity");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerStatisticIncrement(PlayerStatisticIncrementEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerStatisticIncrement");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerSwapHandItems(PlayerSwapHandItemsEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerSwapHandItems");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerTeleport(PlayerTeleportEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerTeleport");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerToggleFlight(PlayerToggleFlightEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerToggleFlight");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerToggleSneak(PlayerToggleSneakEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerToggleSneak");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerToggleSprint(PlayerToggleSprintEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerToggleSprint");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerUnleashEntity(PlayerUnleashEntityEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerUnleashEntity");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void playerUnregisterChannel(PlayerUnregisterChannelEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerUnregisterChannel");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void playerVelocity(PlayerVelocityEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("playerVelocity");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }
}