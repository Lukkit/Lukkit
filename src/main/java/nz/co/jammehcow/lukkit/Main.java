package nz.co.jammehcow.lukkit;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginLoader;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jammehcow
 */

public class Main extends JavaPlugin {
    // Config version
    private static final int CFG_VERSION = 3;

    static Logger logger;
    public static Main instance;

    PluginManager pluginManager;

    @Override
    public void onEnable() {
        if (getConfig().get("update-checker").equals(true))
            UpdateChecker.checkForUpdates(getDescription().getVersion());

        this.getCommand("lukkit").setTabCompleter(new TabCompleter());

        // Subtract one to count for Lukkit being loaded.
        int totalPlugins = pluginManager.getPlugins().length - 1;

        if (totalPlugins > 0) {
            this.getLogger().info((totalPlugins != 1) ? totalPlugins + " Lukkit plugins were loaded" : "1 Lukkit plugins was loaded");
        } else {
            this.getLogger().info("No Lukkit plugins were loaded.");
        }
    }

    @Override
    public void onDisable() {
        LuaEnvironment.shutdown();
    }

    @Override
    public void onLoad() {
        logger = this.getLogger();
        instance = this;

        if (!this.getDataFolder().exists()) //noinspection ResultOfMethodCallIgnored
            this.getDataFolder().mkdir();

        this.checkConfig();

        LuaEnvironment.init(this.getConfig().getBoolean("lua-debug"));

        // Register the Lukkit plugin loader with the plugin manager.
        this.getServer().getPluginManager().registerInterface(LukkitPluginLoader.class);

        this.pluginManager = this.getServer().getPluginManager();

        this.getLogger().info("Loading Lukkit plugins...");

        File[] plugins = this.getFile().getParentFile().listFiles();

        if (plugins != null) {
            for (File file : plugins) {
                for (Pattern filter : LukkitPluginLoader.fileFilters) {
                    Matcher match = filter.matcher(file.getName());

                    if (match.find()) {
                        try { this.pluginManager.loadPlugin(file); }
                        catch (InvalidPluginException | InvalidDescriptionException e) { e.printStackTrace(); }
                    }
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().startsWith("lukkit")) {
            if (args.length != 0) {
                String cmd = args[0];
                args = (String[]) ArrayUtils.remove(args, 0);

                if (cmd.equalsIgnoreCase("help")) {
                    sender.sendMessage(getHelpMessage());
                } else if (cmd.equalsIgnoreCase("reload")) {
                    if (!(args.length == 0 || args[0].equals(""))) {
                        LukkitPlugin plugin = (LukkitPlugin) pluginManager.getPlugin(args[0]);
                        if (plugin != null) {
                            sender.sendMessage("Reloading " + plugin.getName());
                            ((LukkitPluginLoader) plugin.getPluginLoader()).reloadPlugin(plugin);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Plugin " + args[0] + " was not loaded. Try " + ChatColor.YELLOW + "/lukkit plugins");
                        }
                    } else {
                        LuaEnvironment.shutdown();
                        this.getLogger().info("Reloading all Lukkit plugins...");
                        LuaEnvironment.init(this.getConfig().getBoolean("lua-debug"));
                        this.iteratePlugins(LukkitPlugin::onEnable);
                        this.getLogger().info(ChatColor.GREEN + "Reloaded plugins!");
                    }
                    return true;
                } else if (cmd.equalsIgnoreCase("plugins")) {
                    Plugin[] plugins = pluginManager.getPlugins();
                    StringBuilder sb = new StringBuilder().append(ChatColor.GREEN).append("Lukkit Plugins:").append(ChatColor.YELLOW);

                    for (Plugin p : plugins) {
                        if (p != this) {
                            // Prevents Lukkit from being added as a plugin.
                            sb.append("\n  - ").append(p.getName());
                            if (p.getDescription().getDescription() != null) {
                                sb.append(": ").append(p.getDescription().getDescription());
                            }
                        }
                    }

                    sender.sendMessage(sb.toString());
                    return true;
                }
            } else {
                sender.sendMessage(getHelpMessage());
                return true;
            }
        }
        return false;
    }

    private void checkConfig() {
        File cfg = new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
        if (!cfg.exists()) this.saveDefaultConfig();

        if (this.getConfig().getInt("cfg-version") != CFG_VERSION) {
            this.getLogger().info("Your config is out of date. Replacing the config with the default copy and moving the old version to config.old.yml");
            File bkpCfg = new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.old.yml");
            try {
                Files.copy(cfg.toPath(), bkpCfg.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.delete(cfg.toPath());
                this.saveDefaultConfig();
            } catch (IOException e) {
                this.getLogger().severe("There was an issue with moving the old config or replacing. Check the stacktrace for more.");
                e.printStackTrace();
            }
        }
    }

    // Needs a better name
    private void iteratePlugins(Consumer<LukkitPlugin> call) {
        for (Plugin plugin : this.pluginManager.getPlugins()) {
            if (plugin != this) {
                call.accept((LukkitPlugin) plugin);
            }
        }
    }

    private String getHelpMessage() {
        return ChatColor.GREEN + "Lukkit commands:\n" +
        ChatColor.YELLOW + "  - \"/lukkit\" - The root command for all commands (shows this message)\n" +
        "  - \"/lukkit help\" - Displays this message\n" +
        "  - \"/lukkit reload [plugin]\" - Reloads all plugins or the specified plugin\n" +
        "  - \"/lukkit run (lua code)\" - Runs the specified code as command arguments\n" +
        "  - \"/lukkit plugins\" - Lists all enabled plugins\n" +
        "  - \"/lukkit stacktrace\" - Gets the last error as a stacktrace\n" +
        "  - \"/lukkit simple-error\" - Returns the last error as a one line message";
    }
}
