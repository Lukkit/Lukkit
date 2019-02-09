package dev.artex.lukkit.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class PluginCompleter implements TabCompleter {
    private static final String[] commands = {
            "list",
    };

    private static final String[] verbs = {
            "reload",
            "reset",
            "enable",
            "disable",
            "info",
            "url",
    };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // "/lukkit plugin <command>"
        // "/lukkit plugin <plugin> <verb>"
        return null;
    }
}
