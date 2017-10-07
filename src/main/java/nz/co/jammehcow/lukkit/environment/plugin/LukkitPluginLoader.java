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
            Pattern.compile("^(.*)\\.lkt$"),
            Pattern.compile("^(.*)\\.lkt\\.zip$")
    };

    /**
     * The Server instance.
     */
    final Server server;

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
    public void reloadPlugin(Plugin plugin) {
        File pluginFile = ((LukkitPlugin) plugin).getFile();
        this.server.getPluginManager().disablePlugin(plugin);

        LukkitPlugin newPlugin = null;

        try {
            newPlugin = (LukkitPlugin) this.loadPlugin(pluginFile);
        } catch (InvalidPluginException e) { e.printStackTrace(); }

        if (newPlugin == null) {
            Main.instance.getLogger().severe("Unable to load the plugin from " + pluginFile.getAbsolutePath());
        } else {
            this.server.getPluginManager().enablePlugin(newPlugin);
        }
    }
}
