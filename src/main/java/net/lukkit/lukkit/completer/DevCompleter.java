package net.lukkit.lukkit.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class DevCompleter implements TabCompleter {
    private static final String[] commands = {
            "pack",
            "unpack",
            "compress",
            "kill",
    };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // "/lukkit plugin list"
        return null;
    }
}
