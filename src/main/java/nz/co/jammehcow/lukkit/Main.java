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
import org.luaj.vm2.LuaError;
import org.reflections.Reflections;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * The Main entry class of the plugin.
 *
 * @author jammehcow
 */
public class Main extends JavaPlugin {
    // Config version
    private static final int CFG_VERSION = 3;
    /**
     * The instance of the plugin. Used for external access by plugin wrappers etc..
     */
    public static Main instance;
    /**
     * The events collected at runtime to match plugin event registrations against.
     */
    public static HashMap<String, Class<? extends Event>> events = new HashMap<>();
    /**
     * The Logger for Lukkit.
     */
    static Logger logger;
    private static long loadTime = 0;

    static {
        // TODO: It works, sure, but it's shit.
        // Get all the events in the Bukkit events package
        Reflections reflections = new Reflections("org.bukkit.event");
        // Iterate through the events and add their name + class object to the events HashMap
        reflections.getSubTypesOf(Event.class).forEach(c -> {
            if (reflections.getSubTypesOf(c).isEmpty()) events.put(c.getSimpleName(), c);
        });
    }

    // The server-wide PluginManager
    private PluginManager pluginManager;
    private LukkitPluginLoader pluginLoader = null;

    private static boolean isLukkitPluginFile(String fileName) {
        for (Pattern pattern : LukkitPluginLoader.fileFilters) {
            if (pattern.matcher(fileName).find()) return true;
        }

        return false;
    }

    private static String getHelpMessage() {
        return ChatColor.GREEN + "Lukkit commands:\n" +
                ChatColor.YELLOW + "  - \"/lukkit\" - The root command for all commands (shows this message)\n" +
                "  - \"/lukkit help\" - Displays this message\n" +
                "  - \"/lukkit run (lua code)\" - Runs the specified code as command arguments\n" +
                "  - \"/lukkit plugins\" - Lists all enabled plugins\n" +
                "  - \"/lukkit dev\" - Contains all developer commands. Prints out the dev help message";
    }

    private static String getDevHelpMessage() {
        return ChatColor.GREEN + "Lukkit dev commands:\n" +
                ChatColor.YELLOW + "  - \"/lukkit dev\" - The root command for developer actions (shows this message)\n" +
                "  - \"/lukkit dev reload (plugin name)\" - Reloads the source file and clears all loaded requires\n" +
                "  - \"/lukkit dev unload (plugin name)\" - Unloads the source file and clears all loaded requires\n" +
                "  - \"/lukkit dev pack (plugin name)\" - Packages the plugin (directory) into a .lkt file for publishing\n" +
                "  - \"/lukkit dev unpack (plugin name)\" - Unpacks the plugin (.lkt) to a directory based plugin\n" +
                "  - \"/lukkit dev last-error\" - Gets the last error thrown by a plugin and sends the message to the sender. Also prints the stacktrace to the console.\n" +
                "  - \"/lukkit dev errors [index]\" - Either prints out all 10 errors with stacktraces or prints out the specified error at the given index [1 - 10]\n" +
                "  - \"/lukkit dev help\" - Shows this message";
    }

    @Override
    public void onEnable() {
        // Check for updates if it's enabled in the config
        if (getConfig().get("update-checker").equals(true))
            UpdateChecker.checkForUpdates(getDescription().getVersion());

        // Set up the tab completer for the /lukkit command
        this.getCommand("lukkit").setTabCompleter(new TabCompleter());

        // Subtract one to count for Lukkit being loaded. Should replace with check internally because other plugins will be registered
        int totalPlugins = (this.pluginLoader == null) ? 0 : this.pluginLoader.loadedPlugins.size();

        if (totalPlugins > 0) {
            this.getLogger().info(((totalPlugins != 1) ? totalPlugins + " Lukkit plugins were loaded" : "1 Lukkit plugin was loaded") + " in " + loadTime + "ms.");
        } else {
            this.getLogger().info("No Lukkit plugins were loaded.");
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onLoad() {
        // Set the logger and instance
        logger = this.getLogger();
        instance = this;

        // Create the data folder directory if it doesn't exist
        if (!this.getDataFolder().exists()) //noinspection ResultOfMethodCallIgnored
            this.getDataFolder().mkdir();

        // Check the config
        this.checkConfig();

        // Initialize the Lua env (sets up globals)
        LuaEnvironment.init(this.getConfig().getBoolean("lua-debug"));

        // Save the plugin manager for future use
        this.pluginManager = this.getServer().getPluginManager();
        // Register our custom plugin loader on the plugin manager
        this.pluginManager.registerInterface(LukkitPluginLoader.class);

        this.getLogger().info("Loading Lukkit plugins...");

        // Get the files in the plugins directory
        File[] plugins = this.getFile().getParentFile().listFiles();

        if (plugins != null) {
            // Set the start time of loading
            long startTime = System.currentTimeMillis();

            for (File file : plugins) {
                // "break" if the file isn't for Lukkit
                if (isLukkitPluginFile(file.getName())) {
                    // Load the plugin using LukkitPluginLoader
                    try {
                        this.pluginManager.loadPlugin(file);
                    } catch (InvalidPluginException | InvalidDescriptionException e) {
                        LuaEnvironment.addError(e);
                        e.printStackTrace();
                    }
                }
            }

            // Get the total time to load plugins and save to loadTime member
            loadTime = System.currentTimeMillis() - startTime;
        }

        for (Plugin plugin : this.pluginManager.getPlugins()) {
            if (plugin instanceof LukkitPlugin) {
                this.pluginLoader = (LukkitPluginLoader) plugin.getPluginLoader();
                break;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().startsWith("lukkit"))
            return false;
        if (args.length == 0) {
            sender.sendMessage(getHelpMessage());
            return true;
        }

        // Set the String "cmd" to the first arg and remove the arg from the "args" array.
        String cmd = args[0];
        // Get a new array with the first arg omitted
        args = (String[]) ArrayUtils.remove(args, 0);

        if (cmd.equalsIgnoreCase("help")) {
            // Send the help message to the user
            sender.sendMessage(getHelpMessage());
        } else if (cmd.equalsIgnoreCase("plugins")) {
            // Create a new StringBuilder object with "Lukkit Plugins:" as a prefix.
            StringBuilder sb = new StringBuilder().append(ChatColor.GREEN).append("Lukkit Plugins:").append(ChatColor.YELLOW);

            this.iteratePlugins((p) -> {
                // Add the name to the list
                sb.append("\n  - ").append(p.getName());
                // Check if a description for the plugin exists
                if (p.getDescription().getDescription() != null) {
                    // Add the description to the line
                    sb.append(": ").append(p.getDescription().getDescription());
                }
            });

            // Send the message
            sender.sendMessage(sb.toString());
            return true;
        } else if (cmd.equalsIgnoreCase("dev")) {
            if (args.length == 0) {
                // Send the dev help message
                sender.sendMessage(getDevHelpMessage());
            } else if (args[0].equalsIgnoreCase("reload")) {
                // Create a new HashMap to store LukkitPlugins by name
                HashMap<String, LukkitPlugin> plugins = new HashMap<>();
                // Iterate over the plugins and add them to the map by lower-cased name
                this.iteratePlugins(p -> plugins.put(p.getName().toLowerCase(), p));

                String pluginName = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).toLowerCase();

                if (plugins.containsKey(pluginName)) {
                    LukkitPlugin plugin = plugins.get(pluginName);
                    try {
                        ((LukkitPluginLoader) plugin.getPluginLoader()).reloadPlugin(plugin);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "There was an error reloading this plugin: " + e.getMessage() + "\nCheck the console for more information.");
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage("The specified plugin \"" + args[1] + "\" does not exist.");
                }
            } else if (args[0].equalsIgnoreCase("unload")) {
                // Create a new HashMap to store LukkitPlugins by name
                HashMap<String, LukkitPlugin> plugins = new HashMap<>();
                // Iterate over the plugins and add them to the map by lower-cased name
                this.iteratePlugins(p -> plugins.put(p.getName().toLowerCase(), p));

                String pluginName = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).toLowerCase();

                if (plugins.containsKey(pluginName)) {
                    LukkitPlugin plugin = plugins.get(pluginName);
                    try {
                        ((LukkitPluginLoader) plugin.getPluginLoader()).unloadPlugin(plugin);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "There was an error unloading this plugin: " + e.getMessage() + "\nCheck the console for more information.");
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage("The specified plugin \"" + args[1] + "\" does not exist.");
                }
            } else if (args[0].equalsIgnoreCase("pack")) {
                // Zip the plugin
                this.zipOperation(ZipOperation.PACKAGE, sender, args);
            } else if (args[0].equalsIgnoreCase("unpack")) {
                // Unzip the plugin
                this.zipOperation(ZipOperation.UNPACK, sender, args);
            } else if (args[0].equalsIgnoreCase("last-error")) {
                Optional<Exception> err = LuaEnvironment.getLastError();
                if (err.isPresent()) {
                    sender.sendMessage(err.get().getMessage());
                    err.get().printStackTrace();
                } else {
                    sender.sendMessage("There was no error to get.");
                }
            } else if (args[0].equalsIgnoreCase("errors")) {
                // Get all the errors off of the stack
                Optional<Stream<Exception>> errors = LuaEnvironment.getErrors();

                // Check if the errors list equals null (returned if empty)
                if (errors.isPresent()) {
                    // Check if the only arg is the "errors" sub-command
                    if (args.length == 1) {
                        // Get all the non-null error objects

                        errors.get().forEach(exception -> {
                            // Send each error message to the player and print the stack trace
                            sender.sendMessage(exception.getMessage());
                            exception.printStackTrace();
                        });
                    } else {
                        try {
                            // Get the error at the specified index
                            LuaError error = ((LuaError[]) errors.get().toArray())[Integer.parseInt(args[2])];

                            // Send the error message to the player and print the stack trace
                            sender.sendMessage(error.getMessage());
                            error.printStackTrace();
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + args[1] + " cannot be converted to an integer.");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            sender.sendMessage(ChatColor.RED + args[1] + " is out of bounds in the stack. Should be between 1 & " + errors.get().count());
                        }
                    }
                } else {
                    sender.sendMessage("There are no errors to display!");
                }
            } else sender.sendMessage(getDevHelpMessage());
        }

        return true;
    }

    private void checkConfig() {
        // Get the config by relative path
        File cfg = new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
        // Save the config if it doesn't exist
        if (!cfg.exists()) this.saveDefaultConfig();

        // Check the config version against the internal version
        if (this.getConfig().getInt("cfg-version") != CFG_VERSION) {
            this.getLogger().info("Your config is out of date. Replacing the config with the default copy and moving the old version to config.old.yml");

            // Create a new place for the old config to live
            File bkpCfg = new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.old.yml");
            try {
                // Copy the config to the new path and delete the old one, essentially moving it
                Files.copy(cfg.toPath(), bkpCfg.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.delete(cfg.toPath());
                // Save the internal config to the data folder
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

    private enum ZipOperation {
        /**
         * Zip operation.
         */
        PACKAGE,
        /**
         * Unzip operation.
         */
        UNPACK
    }
}
