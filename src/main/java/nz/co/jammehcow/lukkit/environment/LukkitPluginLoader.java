package nz.co.jammehcow.lukkit.environment;

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
    public static ArrayList<LukkitPlugin> plugins = new ArrayList<>();
    /**
     * The list of plugins that are loaded into memory and enabled (or being loaded on reload).
     */
    public static ArrayList<LukkitPlugin> loadedPlugins = new ArrayList<>();

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
        return null;
    }

    @Override
    public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
        return null;
    }

    @Override
    public Pattern[] getPluginFileFilters() {
        return new Pattern[0];
    }

    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {
        return null;
    }

    @Override
    public void enablePlugin(Plugin plugin) {

    }

    @Override
    public void disablePlugin(Plugin plugin) {

    }
}
