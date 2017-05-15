package nz.co.jammehcow.lukkit.environment;

import nz.co.jammehcow.lukkit.Main;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * The type Lukkit plugin loader.
 *
 * @author jammehcow
 */
public class LukkitPluginLoader implements org.bukkit.plugin.PluginLoader {
    /**
     * The list of available plugins installed in the Lukkit data folder.
     * Plugins aren't loaded by default due to dependency requirements.
     * If we get a list of every plugin installed we can check dependency requests against the plugins available and throw errors when they don't exist (for hard depends).
     */
    public static ArrayList<LukkitPlugin> loadedPlugins = new ArrayList<>();
    /**
     * The list of plugins that are loaded into memory and enabled (or being loaded on reload).
     */
    public static ArrayList<LukkitPlugin> enabledPlugins = new ArrayList<>();

    private final Pattern[] fileFilters = { Pattern.compile("\\.lkt$") };

    final Server server;

    public LukkitPluginLoader(Server server) {
        this.server = server;
    }

    /**
     * Load all plugins.
     */
    public void loadAllPlugins() {
        File folder = Main.instance.getDataFolder();
        if (!folder.exists()) folder.mkdir();

        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            for (File f : files) {
                // I'm only checking if it ends with .lkt in case it's a directory (which is still usable)
                if (f.getAbsolutePath().endsWith(".lkt")) {
                    LukkitPluginFile lpf = new LukkitPluginFile(f);
                    loadedPlugins.add(new LukkitPlugin(this, lpf));
                } else if (f.isDirectory() && (new File(f.getAbsolutePath() + File.separator + "main.lua")).exists()) {
                    // TODO
                }
            }
        }
    }

    private void enableAllPlugins() {
        loadedPlugins.forEach(this::enablePlugin);
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
        // TODO
        return null;
    }

    @Override
    public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
        // TODO
        return null;
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
        // TODO
        plugin.onEnable();
    }

    /**
     * Disable all LukkitPlugin objects in the loadedPlugins list.
     */
    public void disableAll() {
        enabledPlugins.forEach(this::disablePlugin);
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        plugin.onDisable();
        enabledPlugins.remove(plugin);
    }
}
