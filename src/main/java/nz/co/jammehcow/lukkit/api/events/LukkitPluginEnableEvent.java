package nz.co.jammehcow.lukkit.api.events;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LukkitPluginEnableEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private final LukkitPlugin plugin;

    public LukkitPluginEnableEvent(LukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public LukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
