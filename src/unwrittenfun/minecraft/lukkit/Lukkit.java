package unwrittenfun.minecraft.lukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.LuaValue;
import unwrittenfun.minecraft.lukkit.environment.LukkitEnvironment;
import unwrittenfun.minecraft.lukkit.environment.LukkitPlugin;
import unwrittenfun.minecraft.lukkit.environment.LukkitPluginLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class Lukkit extends JavaPlugin {

    // Config version
    final int CFG_VERSION = 2;

    // Init the plugin instance and logger
    public static Lukkit instance;
    public static Logger logger;

    @Override
    public void onEnable() {
        // Set the instance to the Lukkit plugin instance
        instance = this;
        // Set the logger to the current logger
        logger = getLogger();


        // Set up LuaJ & Lukkit environment (events, libs etc.)
        LukkitEnvironment.loadEnvironment();
        // Load plugins from Lukkit data folder
        LukkitEnvironment.loadPlugins();

        // Update messages
        if (getConfig().get("update-checker").equals(true)) {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(
                        "http://www.spigotmc.org/api/general.php").openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=32599").getBytes("UTF-8"));
                String version = new BufferedReader(new InputStreamReader(
                        con.getInputStream())).readLine();
                if (version.length() <= 7) {
                    getLogger().info("A new version of Lukkit has been released: " + version);
                    getLogger().info("You can download it from https://www.spigotmc.org/resources/lukkit.32599/");
                }
            } catch (Exception ex) {
                getLogger().info("Unable to connect to Spigot API for Lukkit update check.");
            }
        }

        File cfg = new File(getDataFolder(), "config.yml");

        if (!cfg.exists() || !getConfig().getKeys(true).contains("cfg-version")) {
            getLogger().warning("config.yml was either missing or corrupt. Replacing with default config.");
            // Save default config to location.
            instance.saveDefaultConfig();
        } else if (!getConfig().get("cfg-version").equals(CFG_VERSION)) {
            logger.info("Your config is out of date. Please consider updating by using the config proved on the GitHub wiki.");
        }

        // Set the TabCompleter for custom completions of the /lukkit command
        getCommand("lukkit").setTabCompleter(new ConstructTabCompleter());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lukkit") && args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {

                // Reload the lukkit environment and plugins
                LukkitEnvironment.loadEnvironment();
                LukkitEnvironment.loadPlugins();
                sender.sendMessage(ChatColor.YELLOW + "Lukkit environment and plugins reloaded.");

                return true;

            } else if (args[0].equalsIgnoreCase("run") && args.length > 1) {

                if (sender.hasPermission("lukkit.run") && getConfig().get("can-run-code").equals(true)) {
                    String code = "";

                    // Add all args as one complete string (with spaces) to String code
                    for (int i = 1; i < args.length; i++) {
                        code += args[i] + " ";
                    }

                    // Run the string as code through LuaJ's interpreter
                    LuaValue result = LukkitEnvironment.runString(code);

                    // Send the user the lua error if errored

                    if (result.isstring() && result.toString().equals("ERROR")) {
                        sender.sendMessage(ChatColor.RED + LukkitEnvironment.lastError);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to run Lua code. Safety first!");
                }

                return true;

            } else if (args[0].equalsIgnoreCase("resetenv")) {

                // Reload the lukkit environment
                LukkitEnvironment.loadEnvironment();
                sender.sendMessage(ChatColor.YELLOW + "Lukkit environment reset.");

                return true;

            } else if (args[0].equalsIgnoreCase("last-error")) {

                // Reload the lukkit environment
                LukkitEnvironment.loadEnvironment();
                sender.sendMessage(ChatColor.RED + LukkitEnvironment.lastError);

                return true;

            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(new String[] {
                        ChatColor.YELLOW + "Lukkit Usage: ",
                        ChatColor.YELLOW + "  /lukkit [sub-command]", "",
                        ChatColor.YELLOW + "Lukkit sub-commands: ",
                        ChatColor.YELLOW + "  reload - Reload the lua environment and plugins.",
                        ChatColor.YELLOW + "  resetenv - Reset the lua environment",
                        ChatColor.YELLOW + "  run [code] - Run text after 'run' as lua code",
                        ChatColor.YELLOW + "  last-error - Print the last lua error message"
                });

                return true;

            } else if (args[0].equalsIgnoreCase("plugins")) {

                if (!LukkitPluginLoader.loadedPlugins.isEmpty()) {
                    // Create a new array of LukkitPlugins inheriting size from LukkitPluginLoader.loadedPlugins
                    LukkitPlugin[] tempArr = new LukkitPlugin[LukkitPluginLoader.loadedPlugins.size()];
                    LukkitPluginLoader.loadedPlugins.toArray(tempArr);

                    String pluginsList = "";
                    for (Plugin p : tempArr) {
                        if (p.getDescription().getDescription() != null) {
                            pluginsList += "  " + ChatColor.GREEN + p.getName() + ChatColor.YELLOW + " - " + p.getDescription().getDescription();
                        } else {
                            // This will happen for all plugins for the time being
                            pluginsList += "  " + p.getDescription().getFullName() + " - No description provided.";
                        }
                    }

                    sender.sendMessage(new String[] {
                            ChatColor.YELLOW + "Plugins loaded:",
                            ChatColor.YELLOW + pluginsList
                    });
                } else {
                    sender.sendMessage("No Lukkit plugins loaded!");
                }

                return true;

            }
        } else  {

            sender.sendMessage(ChatColor.YELLOW + "No arguments specified. Try " + ChatColor.BOLD + "/lukkit help" + ChatColor.RESET + ChatColor.YELLOW + " for more information.");

            return true;

        }

        return false;

    }
}
