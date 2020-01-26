package net.lukkit.lukkit.command.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.HashMap;
import java.util.List;

public class HelpCompleter implements TabCompleter {
    // TODO
    private static final HashMap<String, String> helpTexts = new HashMap<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
