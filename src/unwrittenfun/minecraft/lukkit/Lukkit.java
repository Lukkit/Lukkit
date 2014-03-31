package unwrittenfun.minecraft.lukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.LuaValue;
import unwrittenfun.minecraft.lukkit.environment.LukkitEnvironment;

import java.util.logging.Logger;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
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
            }
        }

        return false;
    }
}
