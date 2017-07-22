package nz.co.jammehcow.lukkit;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginLoader;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
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

    private enum ZipOperation {
        PACKAGE,
        UNPACK
    }

    public static HashMap<String, Class<? extends Event>> events = new HashMap<>();
    static {
        // TODO: It works, sure, but it's shit.
        Reflections reflections = new Reflections("org.bukkit.event");
        reflections.getSubTypesOf(Event.class).forEach(c -> {
            if (reflections.getSubTypesOf(c).isEmpty()) events.put(c.getSimpleName(), c);
        });
    }

    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        if (getConfig().get("update-checker").equals(true))
            UpdateChecker.checkForUpdates(getDescription().getVersion());

        this.getCommand("lukkit").setTabCompleter(new TabCompleter());

        // Subtract one to count for Lukkit being loaded.
        int totalPlugins = pluginManager.getPlugins().length - 1;

        if (totalPlugins > 0) {
            this.getLogger().info((totalPlugins != 1) ? totalPlugins + " Lukkit plugins were loaded" : "1 Lukkit plugin was loaded");
        } else {
            this.getLogger().info("No Lukkit plugins were loaded.");
        }
    }

    @Override
    public void onDisable() {}

    @Override
    public void onLoad() {
        logger = this.getLogger();
        instance = this;

        if (!this.getDataFolder().exists()) //noinspection ResultOfMethodCallIgnored
            this.getDataFolder().mkdir();

        this.checkConfig();

        LuaEnvironment.init(this.getConfig().getBoolean("lua-debug"));

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
                // Set the String "cmd" to the first arg and remove the arg from the "args" array.
                String cmd = args[0];
                args = (String[]) ArrayUtils.remove(args, 0);

                if (cmd.equalsIgnoreCase("help")) {
                    sender.sendMessage(getHelpMessage());
                } else if (cmd.equalsIgnoreCase("plugins")) {
                    StringBuilder sb = new StringBuilder().append(ChatColor.GREEN).append("Lukkit Plugins:").append(ChatColor.YELLOW);

                    this.iteratePlugins((p) -> {
                        sb.append("\n  - ").append(p.getName());
                        if (p.getDescription().getDescription() != null) {
                            sb.append(": ").append(p.getDescription().getDescription());
                        }
                    });

                    sender.sendMessage(sb.toString());
                    return true;
                } else if (cmd.equalsIgnoreCase("dev")) {
                    if (args[2] == null) {
                        sender.sendMessage(getDevHelpMessage());
                    } else if (args[2].equalsIgnoreCase("reload")) {
                        HashMap<String, LukkitPlugin> plugins = new HashMap<>();
                        this.iteratePlugins(p -> plugins.put(p.getName().toLowerCase(), p));

                        if (plugins.containsKey(args[2].toLowerCase())) {
                            // TODO later
                        } else {
                            sender.sendMessage("The specified plugin " + args[2] + " does not exist.");
                        }
                    } else if (args[2].equalsIgnoreCase("pack")) {
                        this.zipOperation(ZipOperation.PACKAGE, sender, args);
                    } else if (args[2].equalsIgnoreCase("unpack")) {
                        this.zipOperation(ZipOperation.UNPACK, sender, args);
                    } else sender.sendMessage(getDevHelpMessage());
                }
            } else sender.sendMessage(getHelpMessage());

            return true;
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

    private void iteratePlugins(Consumer<LukkitPlugin> call) {
        for (Plugin plugin : this.pluginManager.getPlugins()) {
            if (plugin instanceof LukkitPlugin) {
                call.accept((LukkitPlugin) plugin);
            }
        }
    }

    private void zipOperation(ZipOperation operation, CommandSender sender, String[] args) {
        if (args[1] != null) {
            HashMap<String, LukkitPlugin> plugins = new HashMap<>();
            this.iteratePlugins(p -> plugins.put(p.getName().toLowerCase(), p));

            LukkitPlugin plugin = plugins.get(args[1]);

            if (plugin != null) {
                if ((operation == ZipOperation.PACKAGE) == plugin.isDevPlugin()) {
                    if (operation == ZipOperation.PACKAGE) {
                        ZipUtil.unexplode(plugin.getFile());
                    } else {
                        ZipUtil.explode(plugin.getFile());
                    }
                } else {
                    sender.sendMessage("The specified plugin \"" + plugin.getName() + "\" is already " + ((operation == ZipOperation.PACKAGE) ? "packaged" : "unpacked") + ".");
                }
            } else {
                sender.sendMessage("The specified plugin \"" + args[1] + "\" does not exist.");
            }
        } else {
            sender.sendMessage("You didn't specify a plugin to " + ((operation == ZipOperation.PACKAGE) ? "package" : "unpack") + "!");
        }
    }

    private static String getHelpMessage() {
        return ChatColor.GREEN + "Lukkit commands:\n" +
                ChatColor.YELLOW + "  - \"/lukkit\" - The root command for all commands (shows this message)\n" +
                "  - \"/lukkit help\" - Displays this message\n" +
                "  - \"/lukkit run (lua code)\" - Runs the specified code as command arguments\n" +
                "  - \"/lukkit plugins\" - Lists all enabled plugins\n" +
                "  - \"/lukkit stacktrace\" - Gets the last error as a stacktrace\n" +
                "  - \"/lukkit simple-error\" - Returns the last error as a one line message" +
                "  - \"/lukkit dev\" - Contains all developer commands. Prints out the dev help message";
    }

    private static String getDevHelpMessage() {
        return ChatColor.GREEN + "Lukkit dev commands:\n" +
                ChatColor.YELLOW + "  - \"/lukkit dev\" - The root command for developer actions (shows this message)\n" +
                "  - \"/lukkit dev reload (plugin name)\" - Reloads the source file and clears all loaded requires\n" +
                "  - \"/lukkit dev pack (plugin name)\" - Packages the plugin (directory) into a .lkt file for publishing\n" +
                "  - \"/lukkit dev unpack (plugin name)\" - Unpacks the plugin (.lkt) to a directory based plugin\n" +
                "  - \"/lukkit dev help\" - Shows this message";
    }
}
