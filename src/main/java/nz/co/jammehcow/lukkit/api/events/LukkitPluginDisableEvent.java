package nz.co.jammehcow.lukkit.api.events;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LukkitPluginDisableEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final LukkitPlugin plugin;

    public LukkitPluginDisableEvent(LukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public LukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
