package net.lukkit.lukkit.command;

import net.lukkit.lukkit.Permissions;
import net.lukkit.lukkit.util.MessageSender;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PluginCommand extends ICommand {
    @Override
    public boolean handle(@NotNull JavaPlugin plugin, @NotNull CommandSender sender, @NotNull String cmd, String[] args) {
        // Doesn't need firstArg
        if (cmd.equalsIgnoreCase("restart-all")) {
            cmdRestartAll(sender);
            return true;
        }

        String firstArg = (args == null || args.length < 1) ? null : args[0];
        switch (cmd.toLowerCase()) {
            case "info":
                if (isPermissible(sender, Permissions.PLUGIN_INFO)) cmdInfo(sender, firstArg);
                break;
            case "start":
                if (isPermissible(sender, Permissions.PLUGIN_START)) cmdStart(sender, firstArg);
                break;
            case "stop":
                if (isPermissible(sender, Permissions.PLUGIN_STOP)) cmdStop(sender, firstArg);
                break;
            case "enable":
                if (isPermissible(sender, Permissions.PLUGIN_ENABLE)) cmdEnable(sender, firstArg);
                break;
            case "disable":
                if (isPermissible(sender, Permissions.PLUGIN_DISABLE)) cmdDisable(sender, firstArg);
                break;
            case "reload":
                if (isPermissible(sender, Permissions.PLUGIN_RELOAD)) cmdReload(sender, firstArg);
                break;
            case "restart":
                // If they have RESTART_ALL then they have implicit access to RESTART_SINGLE
                if (isPartiallyPermissible(sender, Permissions.PLUGIN_RESTART_ALL, Permissions.PLUGIN_RESTART_SINGLE)) {
                    cmdRestart(sender, firstArg);
                }
                break;
            case "restart-all":
                if (isPermissible(sender, Permissions.PLUGIN_RESTART_ALL)) cmdRestartAll(sender);
                break;
            case "clean":
                if (isPermissible(sender, Permissions.PLUGIN_CLEAN)) cmdClean(sender, firstArg);
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Get info about the locally installed plugin
     *
     * Command: /lukkit plugin info (plugin)
     * Associated permissions:
     *      lukkit.plugin.info - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to fetch information for
     */
    private void cmdInfo(@NotNull CommandSender sender, @Nullable String pluginName) {
        // TODO
    }

    /**
     * Starts the specified plugin (if not already started)
     *
     * Command: /lukkit plugin start (plugin)
     * Associated permissions:
     *      lukkit.plugin.start - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to start
     */
    private void cmdStart(@NotNull CommandSender sender, @Nullable String pluginName) {
        // TODO
    }

    /**
     * Stops the specified plugin (if running)
     *
     * Command: /lukkit plugin stop (plugin)
     * Associated permissions:
     *      lukkit.plugin.stop - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to stop
     */
    private void cmdStop(@NotNull CommandSender sender, @Nullable String pluginName) {
        // TODO
    }

    /**
     * Enables the plugin to be run when the server is started
     *
     * Command: /lukkit plugin enable (plugin)
     * Associated permissions:
     *      lukkit.plugin.enable - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to enable
     */
    private void cmdEnable(@NotNull CommandSender sender, @Nullable String pluginName) {
        // TODO
    }

    /**
     * Disables the plugin being run when the server is started
     *
     * Command: /lukkit plugin disable (plugin)
     * Associated permissions:
     *      lukkit.plugin.disable - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to disable
     */
    private void cmdDisable(@NotNull CommandSender sender, @Nullable String pluginName) {
        // TODO
    }

    /**
     * Reloads the plugin's configuration and files (if loaded in the onLoad and onEnable blocks)
     * Execution and plugin state is not affected by a reload, the onLoad and onEnable blocks are re-called
     *
     * Command: /lukkit plugin reload (plugin)
     * Associated permissions:
     *      lukkit.plugin.reload - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to reload
     */
    private void cmdReload(@NotNull CommandSender sender, @Nullable String pluginName) {
        if (pluginName == null) {
            MessageSender.sendMessage(sender, "&cYou need to provide a plugin to reload!");
            return;
        }
        // TODO
    }

    /**
     * Equivalent to running stop then start, just restarts
     *
     * Command: /lukkit plugin restart (plugin)
     * Associated permissions:
     *      lukkit.plugin.restart.single - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to restart
     */
    private void cmdRestart(@NotNull CommandSender sender, @Nullable String pluginName) {
        if (pluginName == null) {
            MessageSender.sendMessage(sender, "&cYou need to provide a plugin to restart!\n" +
                    "&eIf you wish to restart all plugin use restart-all");
            return;
        }
    }

    /**
     * Restarts all plugins loaded by Lukkit
     *
     * Command: /lukkit plugin restart-all
     * Associated permissions:
     *      lukkit.plugin.restart.all - Allows usage of the command
     *
     *
     * @param sender the sender of the command
     */
    private void cmdRestartAll(@NotNull CommandSender sender) {
        // TODO
    }

    /**
     * Cleans out all plugin-created files or files created by the specified plugin
     *
     * Command: /lukkit plugin clean [name]
     * Associated permissions:
     *      lukkit.plugin.clean.single - Allows cleaning of a single plugin
     *      lukkit.plugin.clean.all - Allows cleaning of all Lukkit plugins
     *
     *
     * @param sender the sender of the command
     * @param pluginName (optional) the name of the plugin to clean
     */
    private void cmdClean(@NotNull CommandSender sender, @Nullable String pluginName) {
        // TODO

        if (pluginName == null) {
            MessageSender.sendMessage(sender, "&cYou need to provide a plugin to clean!");
            return;
        }
    }
}
