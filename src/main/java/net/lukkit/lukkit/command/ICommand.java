package net.lukkit.lukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface ICommand {
    /**
     * Handles the requested command
     *
     * @param plugin the plugin (in this case Lukkit)
     * @param sender the sender of the command
     * @param cmd the command (arg[1] in relation to the onCommand function)
     * @param args any arguments that exist
     *
     * @return success of the run command
     */
    boolean handle(@NotNull JavaPlugin plugin, @NotNull CommandSender sender, @NotNull String cmd, String[] args);
}
