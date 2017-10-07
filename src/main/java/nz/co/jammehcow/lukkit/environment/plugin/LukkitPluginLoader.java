package nz.co.jammehcow.lukkit.environment.plugin;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * The type Lukkit plugin loader.
 *
 * @author jammehcow
 */
public class LukkitPluginLoader implements PluginLoader {
    /**
     * The constant fileFilters.
     */
    public static final Pattern[] fileFilters = new Pattern[] {
            Pattern.compile("^(.*)\\.lkt$")
    };

    /**
     * The Server instance.
     */
    private final Server server;

    /**
     * Instantiates a new LukkitPluginLoader.
     *
     * @param server the server
     */
    public LukkitPluginLoader(Server server) {
        this.server = server;
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
        LukkitPlugin plugin = new LukkitPlugin(this, new LukkitPluginFile(file));
        LuaEnvironment.loadedPlugins.add(plugin);
        return plugin;
    }

    @Override
    public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
        try {
            return new PluginDescriptionFile(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new InvalidDescriptionException("The provided file doesn't exist!");
        }
    }

    @Override
    public Pattern[] getPluginFileFilters() {
        return fileFilters;
    }

    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {
        return Main.instance.getPluginLoader().createRegisteredListeners(listener, plugin);
    }

    @Override
    public void enablePlugin(Plugin plugin) {
        plugin.onEnable();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void disablePlugin(Plugin plugin) {
        plugin.onDisable();
        LuaEnvironment.loadedPlugins.remove(plugin);
    }

    /**
     * Reload the specified plugin.
     *
     * @param plugin the {@link LukkitPlugin} object
     */
    public void reloadPlugin(LukkitPlugin plugin) throws InvalidPluginException, InvalidDescriptionException, LukkitPluginException {
        // Check if the plugin is a dev plugin.
        if (!plugin.isDevPlugin()) throw new LukkitPluginException("Cannot reload a standard Lukkit plugin, use /reload instead. This is a developer-only feature.");
        // Get the .lkt plugin file.
        File pluginFile = plugin.getFile();
        // Disable the plugin (also unregisters all events).
        this.server.getPluginManager().disablePlugin(plugin);

        // Create the plugin and load it.
        Plugin newPlugin = this.server.getPluginManager().loadPlugin(pluginFile);
        this.server.getPluginManager().enablePlugin(newPlugin);
    }
}
