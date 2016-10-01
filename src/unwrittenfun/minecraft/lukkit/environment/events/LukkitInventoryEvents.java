
package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitInventoryEvents implements Listener {
    public LukkitInventoryEvents() {
        LukkitEvents.eventMap.put("brew", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("craftItem", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("furnaceBurn", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("furnaceExtract", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("furnaceSmelt", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryClick", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryClose", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryCreative", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryDrag", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryInteract", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryMoveItem", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryOpen", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("inventoryPickupItem", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("prepareAnvil", new ArrayList<LuaFunction>());
        LukkitEvents.eventMap.put("prepareItemCraft", new ArrayList<LuaFunction>());
    }

    @EventHandler
    public void brew(BrewEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("brew");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void craftItem(CraftItemEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("craftItem");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void furnaceBurn(FurnaceBurnEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("furnaceBurn");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void furnaceExtract(FurnaceExtractEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("furnaceExtract");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void furnaceSmelt(FurnaceSmeltEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("furnaceSmelt");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryClick");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryClose");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void inventoryCreative(InventoryCreativeEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryCreative");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void inventoryDrag(InventoryDragEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryDrag");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void inventoryInteract(InventoryInteractEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryInteract");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void inventoryMoveItem(InventoryMoveItemEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryMoveItem");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void inventoryOpen(InventoryOpenEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryOpen");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void inventoryPickupItem(InventoryPickupItemEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("inventoryPickupItem");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }

    @EventHandler
    public void prepareAnvil(PrepareAnvilEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("prepareAnvil");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }

    @EventHandler
    public void prepareItemCraft(PrepareItemCraftEvent event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get("prepareItemCraft");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
        }
    }
}