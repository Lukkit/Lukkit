package nz.co.jammehcow.lukkit.environment;

import com.avaje.ebean.EbeanServer;
import nz.co.jammehcow.lukkit.Main;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.*;
import org.bukkit.plugin.PluginLoader;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author jammehcow
 */

public class LukkitPlugin implements Plugin {
    private String name;
    private File pluginMain;
    private File pluginConfig;
    private LukkitConfigurationFile config;
    private File dataFolder;
    private boolean enabled = false;

    public LukkitPlugin(File main) {
        this.pluginMain = main;
        this.dataFolder = this.pluginMain.getParentFile();
    }

    @Override
    public File getDataFolder() {
        return this.dataFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return null;
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public InputStream getResource(String s) {
        return null;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void saveDefaultConfig() {
        Main.logger.warning("Lukkit plugins don't have the ability to export default configs. saveDefaultConfig() is useless.");
    }

    @Override
    public void saveResource(String s, boolean b) {
        Main.logger.warning("Lukkit plugins doesn't have internal resources. saveResource() is useless.");
    }

    @Override
    public void reloadConfig() { this.config = new LukkitConfigurationFile(this.pluginConfig); }

    @Override
    public PluginLoader getPluginLoader() { return this.getPluginLoader(); }

    @Override
    public Server getServer() { return Main.instance.getServer(); }

    @Override
    public boolean isEnabled() { return LukkitPluginLoader.loadedPlugins.contains(this) && this.enabled; }

    @Override
    public void onEnable() {
        // onEnable callback in file
    }

    @Override
    public void onDisable() {
        // onDisable callback in file
    }

    @Override
    public void onLoad() {

    }

    @Override
    public boolean isNaggable() {
        return false;
    }

    @Override
    public void setNaggable(boolean b) {
        // TODO: lookup
    }

    @Override
    public EbeanServer getDatabase() {
        // ooh a DB!
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String s, String s1) {
        return null;
    }

    @Override
    public Logger getLogger() { return this.getLogger(); }

    @Override
    public String getName() { return this.getDescription().getName(); }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // TODO
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        // TODO
        return null;
    }
}
