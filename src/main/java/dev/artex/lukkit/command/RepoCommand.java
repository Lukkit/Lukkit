package dev.artex.lukkit.command;

import dev.artex.lukkit.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RepoCommand implements ICommand {
    @Override
    public boolean handle(@NotNull JavaPlugin plugin, @NotNull CommandSender sender, @NotNull String cmd, String[] args) {
        String firstArg = (args == null || args.length == 0) ? null : args[0];

        if (cmd.equalsIgnoreCase("help")) {
            cmdHelp(sender, firstArg);
            return true;
        }

        if (cmd.equalsIgnoreCase("list-upgrades")) {
            cmdListUpgrades(sender, firstArg);
            return true;
        }

        // Following commands are marked NotNull for firstArg, error out
        if (firstArg == null) {
            // TODO: error
            return false;
        }

        // Call related function of the command
        String secondArg = (args.length > 1) ? args[1] : null;
        switch (cmd.toLowerCase()) {
            case "info":
                cmdInfo(sender, firstArg);
                break;
            case "search":
                cmdSearch(sender, firstArg);
                break;
            case "versions":
                cmdVersions(sender, firstArg);
                break;
            case "get":
                cmdGet(sender, firstArg, secondArg);
                break;
            case "upgrade":
                cmdUpgrade(sender, firstArg, secondArg);
                break;
            case "downgrade":
                cmdDowngrade(sender, firstArg, secondArg);
                break;
            default:
                return false;
        }

        // Anything other than invalid input reaches here, return true
        return true;
    }

    /**
     * Downloads and installs the Lukkit plugin from the repo
     * User can (optionally) specify a version of the plugin to download
     * Default behaviour is to update to the latest version that supports the installed version of Lukkit
     *
     * Does not get enabled instantly, must be enabled through /lukkit plugin enable [plugin name]
     *
     * Command: /lukkit repo get (plugin) [version]
     * Associated permissions:
     *      lukkit.repo.get - Allows usage of the base command (does not include extra permissions below)
     *      lukkit.repo.get.all - Gives permission to all get commands/features
     *      lukkit.repo.get.force - Allows forcing download when things like Lukkit or MC version
     *          don't match the plugin's config. Will also need to be forced if the plugin already exists
     *      lukkit.repo.get.version - Allows specifying a version to download
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to download
     * @param version (optional) the version of the plugin to download
     */
    private void cmdGet(@NotNull CommandSender sender, @NotNull String pluginName, @Nullable String version) {
        boolean allPermissible = sender.hasPermission(Permissions.REPO_GET_ALL);

        // Skip perm checks if sender has the "all" permission
        if (!allPermissible) {
            if (!sender.hasPermission(Permissions.REPO_GET)) {
                // TODO: sendMessage wrapper
                sender.sendMessage("You do not have permission to use this command");
                return;
            }

            // Permission to specify a version
            if (version != null && !sender.hasPermission(Permissions.REPO_GET_VERSION)) {
                // Sender don't have permission to specify a version
                // TODO: sendMessage wrapper
                sender.sendMessage("You don't have permission to specify a version");
                return;
            }
        }

        // TODO

        if (!sender.hasPermission(Permissions.REPO_GET_FORCE)) {
            // TODO
        }
    }

    /**
     * Upgrades the given Lukkit plugin from the repo
     * User can (optionally) specify a version of the plugin to upgrade to
     * Default behaviour is to upgrade to the latest version that supports the installed version of Lukkit
     *
     * Command: /lukkit repo upgrade (plugin) [version]
     * Associated permissions:
     *      lukkit.repo.upgrade - Allows usage of the base command (does not include extra permissions below)
     *      lukkit.repo.upgrade.all - Gives permission to all update commands/features
     *      lukkit.repo.upgrade.force - Allows forcing an update when things like Lukkit or MC version
     *          don't match the plugin's config
     *      lukkit.repo.upgrade.version - Allows specifying what version to update to
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to attempt an upgrade on
     * @param version (optional) the version of the plugin to upgrade to
     */
    private void cmdUpgrade(@NotNull CommandSender sender, @NotNull String pluginName, @Nullable String version) {
        boolean allPermissible = sender.hasPermission(Permissions.REPO_UPGRADE_ALL);

        // Skip perm checks if sender has the "all" permission
        if (!allPermissible) {
            if (!sender.hasPermission(Permissions.REPO_UPGRADE)) {
                // TODO: sendMessage wrapper
                sender.sendMessage("You do not have permission to use this command");
                return;
            }

            // Permission to specify a version
            if (version != null && !sender.hasPermission(Permissions.REPO_UPGRADE_VERSION)) {
                // Sender don't have permission to specify a version
                // TODO: sendMessage wrapper
                sender.sendMessage("You don't have permission to specify a version");
                return;
            }
        }

        // TODO

        if (!sender.hasPermission(Permissions.REPO_UPGRADE_FORCE)) {
            // TODO
        }
    }

    /**
     * Fetches a list of upgradable plugins or checks if a single plugin can be upgraded
     *
     * Command: /lukkit repo list-upgrades [plugin]
     * Associated permissions:
     *      lukkit.repo.listupgrades - Allows the user to check for upgrades
     *
     * Note: permission "lukkit.repo.upgrade" will allow you to use this command even
     *  if you don't have the above permission
     *
     * @param sender the sender of the command
     */
    private void cmdListUpgrades(@NotNull CommandSender sender, @Nullable String pluginName) {
        if (!sender.hasPermission(Permissions.REPO_UPGRADE) && !sender.hasPermission(Permissions.REPO_LISTUPGRADES)) {
            // TODO: sendMessage wrapper
            sender.sendMessage("You do not have permission to use this command");
            return;
        }

        // TODO
    }

    /**
     * Downgrades the given plugin to the specified version
     *
     * Command: /lukkit repo get (plugin) [version]
     * Associated permissions:
     *      lukkit.repo.downgrade - Allows usage of the base command (does not include extra permissions below)
     *      lukkit.repo.downgrade.all - Gives permission to all downgrade commands/features
     *      lukkit.repo.downgrade.force - Allows forcing a downgrade when things like Lukkit or MC version
     *          don't match the plugin's config
     *      lukkit.repo.downgrade.version - Allows specifying what version to downgrade to
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to downgrade
     * @param version (optional) the version of the plugin to dowgrade to
     */
    private void cmdDowngrade(@NotNull CommandSender sender, @NotNull String pluginName, @Nullable String version) {
        boolean allPermissible = sender.hasPermission(Permissions.REPO_DOWNGRADE_ALL);

        // Skip perm checks if sender has the "all" permission
        if (!allPermissible) {
            if (!sender.hasPermission(Permissions.REPO_DOWNGRADE)) {
                // TODO: sendMessage wrapper
                sender.sendMessage("You do not have permission to use this command");
                return;
            }
        }

        if (version == null) {
            // TODO: interactive version picker?
        }

        // TODO

        if (!sender.hasPermission(Permissions.REPO_DOWNGRADE_FORCE)) {
            // TODO
        }
    }

    /**
     * Search for plugins in the remote plugin repo
     * TODO: regex? filters?
     *
     * Command: /lukkit repo search (plugin)
     * Associated permissions:
     *      lukkit.repo.search - Allows searching for commands
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to search for
     */
    private void cmdSearch(@NotNull CommandSender sender, @NotNull String pluginName) {
        if (!sender.hasPermission(Permissions.REPO_SEARCH)) {
            // TODO: sendMessage wrapper
            sender.sendMessage("You don't have permission to use this command");
        }

        // TODO
    }

    /**
     * Get information about the specified plugin
     * May include: name, description, latest & installed version,
     *      author, link to page, compat versions, downloads etc.
     *
     * Command: /lukkit repo info (plugin)
     * Associated permissions:
     *      lukkit.repo.info - Allows the retrieval of plugin info (local & remote)
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to get info on
     */
    private void cmdInfo(@NotNull CommandSender sender, @NotNull String pluginName) {
        if (!sender.hasPermission(Permissions.REPO_SEARCH)) {
            // TODO: sendMessage wrapper
            sender.sendMessage("You don't have permission to use this command");
        }

        // TODO
    }

    /**
     * List the available and (if applicable) installed versions of the specified plugins
     *
     * Command: /lukkit repo versions (plugin)
     * Associated permissions:
     *      lukkit.repo.versions - Allows the retrieval of plugin info (local & remote)
     *
     *
     * @param sender the sender of the command
     * @param pluginName the name of the plugin to retrieve versions on
     */
    private void cmdVersions(@NotNull CommandSender sender, @NotNull String pluginName) {
        if (!sender.hasPermission(Permissions.REPO_VERSIONS)) {
            // TODO: sendMessage wrapper
            sender.sendMessage("You don't have permission to use this command");
        }

        // TODO
    }

    /**
     * Retrieve help for a repo command
     * NOTE: to get help with a subcommand you must have permission to use it
     *
     * Command: /lukkit repo help [plugin]
     * Associated permissions:
     *      lukkit.repo.help - get help with commands
     *
     * @param subcmd (optional) the subcommand to fetch help for
     */
    private void cmdHelp(@NotNull CommandSender sender, @Nullable String subcmd) {
        if (!sender.hasPermission(Permissions.REPO_HELP)) {
            // TODO: sendMessage wrapper
            sender.sendMessage("You don't have permission to use this command");
        }

        // subcommands
        // check subcommands based on permissions

        // TODO
    }
}
