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
    public static Lukkit instance;
    public static Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        LukkitEnvironment.loadEnvironment();
        LukkitEnvironment.loadPlugins();

        getCommand("lukkit").setTabCompleter(new ConstructTabCompleter());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lukkit") && args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                LukkitEnvironment.loadEnvironment();
                LukkitEnvironment.loadPlugins();
                sender.sendMessage(ChatColor.YELLOW + "Lukkit environment and plugins reloaded.");
                return true;
            } else if (args[0].equalsIgnoreCase("run") && args.length > 1) {
                String code = "";
                for (int i = 1; i < args.length; i++) {
                    code += args[i] + " ";
                }
                LuaValue result = LukkitEnvironment.runString(code);
                if (result.isstring() && result.toString().equals("ERROR")) {
                    sender.sendMessage(ChatColor.RED + LukkitEnvironment.lastError);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("resetenv")) {
                LukkitEnvironment.loadEnvironment();
                sender.sendMessage(ChatColor.YELLOW + "Lukkit environment reset.");
                return true;
            } else if (args[0].equalsIgnoreCase("last-error")) {
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
                    LukkitPlugin[] tempArr = new LukkitPlugin[LukkitPluginLoader.loadedPlugins.size()];
                    LukkitPluginLoader.loadedPlugins.toArray(tempArr);
                    String pluginsList = "";
                    for (Plugin p : tempArr) {
                        if (p.getDescription().getDescription() != null) {
                            pluginsList += "  " + ChatColor.GREEN + p.getName() + ChatColor.YELLOW + " - " + p.getDescription().getDescription() + "\n";
                        } else {
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
