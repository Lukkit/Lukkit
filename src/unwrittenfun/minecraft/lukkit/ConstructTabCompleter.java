package unwrittenfun.minecraft.lukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
 */

public class ConstructTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String cmdLabel, String[] args) {
        if (command.getName().equalsIgnoreCase("lukkit")) {

            // A lidt containing all the sub-commands of /lukkit
            List<String> subCommands = Arrays.asList("reload", "run", "resetenv", "last-error", "help", "plugins");

            // The ArrayList to return upon completion
            ArrayList<String> returnCompletions = new ArrayList<>();

            if (args.length == 1) {
                if (!args[0].equals("")) {
                    // TODO: replace with collect call (remove.steam() stuffs)
                    for (String s : subCommands) {
                        if (s.startsWith(args[0])) {
                            // Add the sub-command to the ArrayList
                            returnCompletions.add(s);
                        }
                    }

                    return returnCompletions;
                } else {

                    return subCommands;
                }
            }
        }

        // If you get this, you've stuffed up
        return Arrays.asList("Error", "what've you done?");
        
    }
}
