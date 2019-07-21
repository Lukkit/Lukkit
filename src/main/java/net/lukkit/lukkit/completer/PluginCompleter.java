package net.lukkit.lukkit.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PluginCompleter implements TabCompleter {
    private static final String[] commands = {
            "list",
    };

    private static final String[] verbs = {
            "info",
            "start",
            "stop",
            "enable",
            "disable",
            "reload",
            "restart",
            "restart-all",
            "clean",
    };

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        // "/lukkit plugin <command>"
        // "/lukkit plugin <plugin> <verb>"
        return null;
    }
}
