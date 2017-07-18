package nz.co.jammehcow.lukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jammehcow
 */

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private static String[] subCommands = {"run", "resetenv", "last-error", "help", "plugins"};
    private static String[] devSubCommands = {"pack", "unpack", "reload", "help"};

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return getFilteredCompletions(args[0], subCommands);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("dev")) {
                return getFilteredCompletions(args[0], devSubCommands);
            }
        }

        return null;
    }

    private List<String> getFilteredCompletions(String arg, String[] subCommands) {
        ArrayList<String> returnCompletions = new ArrayList<>();

        if (!arg.equals("")) {
            for (String s : subCommands) {
                if (s.startsWith(arg)) returnCompletions.add(s);
            }

            return returnCompletions;
        } else return Arrays.asList(subCommands);
    }
}
