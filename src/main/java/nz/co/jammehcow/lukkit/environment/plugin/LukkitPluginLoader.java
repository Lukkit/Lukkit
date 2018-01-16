package nz.co.jammehcow.lukkit.environment.plugin;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.api.events.LukkitPluginDisableEvent;
import nz.co.jammehcow.lukkit.api.events.LukkitPluginEnableEvent;
import nz.co.jammehcow.lukkit.api.events.LukkitPluginLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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
    public static final Pattern[] fileFilters = new Pattern[]{
            Pattern.compile("^(.*)\\.lkt$")
    };
    /**
     * The Server instance.
     */
    private final Server server;
    /**
     * The list of available plugins installed in the Lukkit data folder.
     * Plugins aren't loaded by default due to dependency requirements.
     * If we get a list of every plugin installed we can check dependency requests against the plugins available and throw errors when they don't exist (for hard depends).
     */
    public ArrayList<LukkitPlugin> loadedPlugins = new ArrayList<>();

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
        LukkitPluginFile pluginFile = new LukkitPluginFile(file);

        try {
            PluginDescriptionFile descriptionFile = new PluginDescriptionFile(pluginFile.getPluginYML());
            System.out.println("[" + descriptionFile.getPrefix() + "] Loading " + descriptionFile.getFullName());
        } catch (InvalidDescriptionException e) {
            e.printStackTrace();
            throw new InvalidPluginException("Error while loading " + file.getName() + ".");
        }

        LukkitPluginLoadEvent event = new LukkitPluginLoadEvent(pluginFile);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            LukkitPlugin plugin = new LukkitPlugin(this, pluginFile);
            this.loadedPlugins.add(plugin);
            return plugin;
        }
        return null;
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
        plugin.getLogger().info("Enabling " + plugin.getDescription().getFullName());
        LukkitPluginEnableEvent event = new LukkitPluginEnableEvent((LukkitPlugin) plugin);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            plugin.onEnable();
        } else {
            if (plugin.isEnabled())
                Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void disablePlugin(Plugin plugin) {
        plugin.getLogger().info("Disabling " + plugin.getDescription().getFullName());
        LukkitPluginDisableEvent event = new LukkitPluginDisableEvent((LukkitPlugin) plugin);
        Bukkit.getServer().getPluginManager().callEvent(event);
        HandlerList.unregisterAll(plugin);
        plugin.onDisable();
        this.loadedPlugins.remove(plugin);
    }

    /**
     * Reload the specified plugin.
     *
     * @param plugin the {@link LukkitPlugin} object
     */
    public void reloadPlugin(LukkitPlugin plugin) throws InvalidPluginException, InvalidDescriptionException, LukkitPluginException, IllegalAccessException, NoSuchFieldException, IOException {
        // Check if the plugin is a dev plugin.
        if (!plugin.isDevPlugin())
            throw new LukkitPluginException("Cannot reload a standard Lukkit plugin, use /reload instead. This is a developer-only feature.");

        // Disable and unload the whole plugin
        unloadPlugin(plugin);

        File pluginFile = plugin.getFile();
        // Create the plugin and load it.
        Plugin newPlugin = this.server.getPluginManager().loadPlugin(pluginFile);
        this.server.getPluginManager().enablePlugin(newPlugin);
    }

    /**
     * Unload the specified plugin.
     *
     * @param plugin the {@link LukkitPlugin} object
     */
    public void unloadPlugin(LukkitPlugin plugin) throws InvalidPluginException, InvalidDescriptionException, LukkitPluginException, NoSuchFieldException, IllegalAccessException, IOException {
        // Check if the plugin is a dev plugin.
        if (!plugin.isDevPlugin())
            throw new LukkitPluginException("Cannot unload a standard Lukkit plugin, use /stop instead. This is a developer-only feature.");

        String pName = plugin.getName();
        PluginManager pluginManager = this.server.getPluginManager();

        // Disable the plugin (also unregisters all events).
        pluginManager.disablePlugin(plugin);

        // Get the fields from the plugin manager where the plugin is stored
        Field pluginsField = pluginManager.getClass().getDeclaredField("plugins");
        pluginsField.setAccessible(true);
        Field loaders = pluginManager.getClass().getDeclaredField("lookupNames");
        loaders.setAccessible(true);
        List plugins = (List) pluginsField.get(pluginManager);
        Map names = (Map) loaders.get(pluginManager);

        // Remove the plugin from spigot
        synchronized (pluginManager) {
            if (plugins != null && plugins.contains(plugin)) {
                plugins.remove(plugin);
            }

            if (names != null && names.containsKey(pName)) {
                names.remove(pName);
            }
        }

        // Ask java to clean up
        System.gc();
    }
}
