package dev.artex.lukkit.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class BaseCompleter implements TabCompleter {
    private static final String[] commands = {
            "help",
            "report",
            "dev",
            "plugin"
    };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
