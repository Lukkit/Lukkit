package nz.co.jammehcow.lukkit.environment.plugin;

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
public class LukkitPluginLoader implements PluginLoader {
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

    public static final Pattern[] fileFilters = new Pattern[] {
            Pattern.compile("^(.*)\\.lkt$"),
            Pattern.compile("^(.*)\\.lkt\\.zip$")
    };

    final Server server;

    public LukkitPluginLoader(Server server) {
        this.server = server;
    }

    /**
     * Load all Lukkit plugins.
     */
    public void loadAllPlugins() {
        File folder = new File(Main.instance.getDataFolder().getParent());
        if (!folder.exists()) {
            Main.instance.getLogger().warning("Unable to open the server's plugin directory.");
        }

        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            for (File f : files) {
                LukkitPluginFile lpf = new LukkitPluginFile(f);
                try {
                    loadedPlugins.add(new LukkitPlugin(this, lpf));
                } catch (InvalidPluginException e) {
                    Main.instance.getLogger().warning("The plugin at " + lpf.getPath() + " is invalid. Check the stacktrace for more information.");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
        System.out.println("#loadPlugin called");
        return new LukkitPlugin(this, new LukkitPluginFile(file));
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
        plugin.onEnable();
    }

    /**
     * Reload all LukkitPlugin objects in the loadedPlugins list.
     */
    public void reloadAll() {
        enabledPlugins.forEach(this::disablePlugin);
        this.loadAllPlugins();
        enabledPlugins.forEach(this::enablePlugin);
    }

    public void reloadPlugin(Plugin plugin) {
        File jar = ((LukkitPlugin) plugin).getJarFile();
        this.disablePlugin(plugin);

        LukkitPlugin newPlugin = null;

        try {
            newPlugin = (LukkitPlugin) this.loadPlugin(jar);
        } catch (InvalidPluginException e) { e.printStackTrace(); }

        if (newPlugin == null) {
            Main.instance.getLogger().severe("Unable to load the plugin from " + jar.getAbsolutePath());
        } else {
            this.enablePlugin(newPlugin);
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void disablePlugin(Plugin plugin) {
        plugin.onDisable();
        loadedPlugins.remove(plugin);
        enabledPlugins.remove(plugin);
    }
}
