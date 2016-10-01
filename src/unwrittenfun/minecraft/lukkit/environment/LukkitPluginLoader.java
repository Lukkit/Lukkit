package unwrittenfun.minecraft.lukkit.environment;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitPluginLoader implements PluginLoader {
    public static ArrayList<LukkitPlugin> loadedPlugins = new ArrayList<LukkitPlugin>();

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
        LukkitPlugin lPlugin = (LukkitPlugin) plugin;
        lPlugin.setEnabled(true);
        loadedPlugins.add(lPlugin);
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        LukkitPlugin lPlugin = (LukkitPlugin) plugin;
        lPlugin.setEnabled(false);
        loadedPlugins.remove(lPlugin);
    }

    public void disableAll() {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < loadedPlugins.size(); i++)
            disablePlugin(loadedPlugins.get(i));
    }
}
