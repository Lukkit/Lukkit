package dev.artex.lukkit.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RepoCompleter implements TabCompleter {
    private static final String[] commands = {
            "help",
            "search",
            "get",
            "info",
            "versions",
            "upgrade",
            "list-upgrades",
            "downgrade",
    };

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}