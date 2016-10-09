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

import java.util.logging.Logger;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class Lukkit extends JavaPlugin {

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
                            pluginsList += "  " + ChatColor.GREEN + p.getName() + ChatColor.YELLOW + " - " + p.getDescription().getDescription() + "\n";
                        } else {
                            // This will happen for all plugins for the time being
                            pluginsList += "  " + p.getName() + " - No description provided.\n";
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
