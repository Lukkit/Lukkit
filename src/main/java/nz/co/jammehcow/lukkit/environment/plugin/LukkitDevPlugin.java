package nz.co.jammehcow.lukkit.environment.plugin;

import org.bukkit.plugin.InvalidPluginException;

public class LukkitDevPlugin extends LukkitPlugin {
    /**
     * Instantiates a new Lukkit plugin.
     *
     * @param loader the plugin's loader
     * @param file   the plugin file
     */
    public LukkitDevPlugin(LukkitPluginLoader loader, LukkitPluginFile file) throws InvalidPluginException {
        super(loader, file);
    }
}
