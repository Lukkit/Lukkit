package net.lukkit.lukkit.command;

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
                cmdInfo(sender, firstArg);
                break;
            case "start":
                cmdStart(sender, firstArg);
                break;
            case "stop":
                cmdStop(sender, firstArg);
                break;
            case "enable":
                cmdEnable(sender, firstArg);
                break;
            case "disable":
                cmdDisable(sender, firstArg);
                break;
            case "reload":
                cmdReload(sender, firstArg);
                break;
            case "restart":
                cmdRestart(sender, firstArg);
                break;
            case "clean":
                cmdClean(sender, firstArg);
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
     * Reloads the plugin's configuration and files
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
        // TODO
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
    }

}
