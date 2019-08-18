package net.lukkit.lukkit.command;

import net.lukkit.lukkit.util.MessageSender;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class ICommand {
    private static final String NO_PERMISSION_MESSAGE = "&cYou don't have permission to execute that command (&e%s&c)";

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
    abstract boolean handle(@NotNull JavaPlugin plugin, @NotNull CommandSender sender, @NotNull String cmd, String[] args);

    protected boolean isFullyPermissible(@NotNull CommandSender sender, @NotNull String ... args) {
        for (String perm : args) {
            if (!isPermissible(sender, perm, false)) {
                return false;
            }
        }

        return true;
    }

    boolean isPartiallyPermissible(@NotNull CommandSender sender, @NotNull String... args) {
        for (String perm : args) {
            if (isPermissible(sender, perm, true)) {
                return true;
            }
        }
        MessageSender.sendMessage(sender, String.format(NO_PERMISSION_MESSAGE, String.join(", ", args)));
        return false;
    }

    // Convenience function to de-clutter permission check switches
    boolean isPermissible(@NotNull CommandSender sender, @NotNull String permission) {
        return isPermissible(sender, permission, false);
    }

    @SuppressWarnings("WeakerAccess")
    boolean isPermissible(@NotNull CommandSender sender, @NotNull String permission, boolean silent) {
        if (!sender.hasPermission(permission)) {
            if (!silent) {
                MessageSender.sendMessage(sender, String.format(NO_PERMISSION_MESSAGE, permission));
            }
            return false;
        }

        return true;
    }
}
