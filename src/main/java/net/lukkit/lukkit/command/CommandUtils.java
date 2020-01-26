package net.lukkit.lukkit.command;

import net.lukkit.lukkit.util.MessageSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandUtils {
    private static final String NO_PERMISSION_MESSAGE = "&cYou don't have permission to execute that command (&e%s&c)";

    static protected boolean isFullyPermissible(@NotNull CommandSender sender, @NotNull String ... args) {
        for (String perm : args) {
            if (!isPermissible(sender, perm, false)) {
                return false;
            }
        }

        return true;
    }

    static boolean isPartiallyPermissible(@NotNull CommandSender sender, @NotNull String... args) {
        for (String perm : args) {
            if (isPermissible(sender, perm, true)) {
                return true;
            }
        }
        MessageSender.sendMessage(sender, String.format(NO_PERMISSION_MESSAGE, String.join(", ", args)));
        return false;
    }

    // Convenience function to de-clutter permission check switches
    static boolean isPermissible(@NotNull CommandSender sender, @NotNull String permission) {
        return isPermissible(sender, permission, false);
    }

    @SuppressWarnings("WeakerAccess")
    static boolean isPermissible(@NotNull CommandSender sender, @NotNull String permission, boolean silent) {
        if (!sender.hasPermission(permission)) {
            if (!silent) {
                MessageSender.sendMessage(sender, String.format(NO_PERMISSION_MESSAGE, permission));
            }
            return false;
        }

        return true;
    }
}
