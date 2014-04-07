package unwrittenfun.minecraft.lukkit.environment;

import com.avaje.ebean.EbeanServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginLogger;
import org.luaj.vm2.LuaFunction;
import unwrittenfun.minecraft.lukkit.Lukkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitPlugin extends PluginBase {
    public LuaFunction loadCallback;
    public LuaFunction enableCallback;
    public LuaFunction disableCallback;
    private PluginDescriptionFile description;
    private PluginLogger logger;
    private PluginLoader loader;
    private boolean naggable = true;
    private boolean enabled = true;
    private File pluginFolder;
    private File configFile;
    private YamlConfiguration config;

    public LukkitPlugin(LukkitPluginLoader _loader, String name, String version) {
        description = new PluginDescriptionFile(name, version, "lukkit.plugin." + name);
        logger = new PluginLogger(this);
        loader = _loader;
        pluginFolder = new File(Lukkit.instance.getDataFolder(), name);
        configFile = new File(pluginFolder, "config.yml");
        if (configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
        } else {
            logger.info("No config detected, creating a new one");
            config = new YamlConfiguration();
            this.saveConfig();
        }
    }

    public void setEnabled(boolean enable) {
        enabled = enable;
        if (enabled) onEnable(); else onDisable();
    }

    @Override
    public void onLoad() {
        if (loadCallback != null) loadCallback.call();
    }

    @Override
    public void onEnable() {
        enabled = true;
        if (enableCallback != null) enableCallback.call();
    }

    @Override
    public void onDisable() {
        enabled = false;
        if (disableCallback != null) disableCallback.call();
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public void setNaggable(boolean b) {
        naggable = b;
    }

    @Override
    public boolean isNaggable() {
        return naggable;
    }

    @Override
    public PluginLoader getPluginLoader() {
        return loader;
    }

    @Override
    public File getDataFolder() {
        return pluginFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return description;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public InputStream getResource(String s) {
        return null;
    }

    @Override
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            logger.warning("Cannot save config - IOException");
        }
    }

    @Override
    public void saveDefaultConfig() {
        //TODO: implement
    }

    @Override
    public void saveResource(String s, boolean b) {
        //TODO: implement
    }

    @Override
    public void reloadConfig() {
        //TODO: implement
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public EbeanServer getDatabase() {
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String s, String s2) {
        return getServer().getWorlds().get(0).getGenerator();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
