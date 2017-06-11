package nz.co.jammehcow.lukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jammehcow
 */

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private static String[] subCommands = {"reload", "run", "resetenv", "last-error", "help", "plugins"};

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        ArrayList<String> returnCompletions = new ArrayList<>();

        if (args.length == 1) {
            if (!args[0].equals("")) {
                for (String s : subCommands) {
                    if (s.startsWith(args[0])) {
                        returnCompletions.add(s);
                    }
                }

                return returnCompletions;
            } else {
                return Arrays.asList(subCommands);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reload")) {
                ArrayList<String> plugins = new ArrayList<>();
                for (Plugin plugin : Main.instance.pluginManager.getPlugins()) {
                    if (plugin != Main.instance) {
                        plugins.add(plugin.getName());
                    }
                }

                if (!args[1].equals("")) {
                    for (String s : plugins) {
                        if (!s.toLowerCase().startsWith(args[1].toLowerCase())) {
                            plugins.remove(s);
                        }
                    }
                }

                return plugins;
            }
        }

        return null;
    }
}
