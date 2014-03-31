package unwrittenfun.minecraft.lukkit.environment;

import com.avaje.ebean.EbeanServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginLogger;
import org.luaj.vm2.LuaFunction;

import java.io.File;
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
    private boolean naggable = true;
    private boolean enabled = true;

    public LukkitPlugin(String name, String version) {
        description = new PluginDescriptionFile(name, version, "lukkit.plugin." + name);
        logger = new PluginLogger(this);
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
        return null;
    }

    @Override
    public File getDataFolder() {
        return null;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return description;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Override
    public InputStream getResource(String s) {
        return null;
    }

    @Override
    public void saveConfig() {
        //TODO: implement
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
