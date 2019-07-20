package net.lukkit.lukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PluginCommand implements ICommand {
    @Override
    public boolean handle(@NotNull JavaPlugin plugin, @NotNull CommandSender sender, @NotNull String cmd, @NotNull String[] args) {
        // TODO
        return false;
    }
}
