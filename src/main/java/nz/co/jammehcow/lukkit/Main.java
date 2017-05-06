package nz.co.jammehcow.lukkit;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.LukkitPluginLoader;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author jammehcow
 */

public class Main extends JavaPlugin {
    // Config version
    private static final int CFG_VERSION = 3;

    public static Logger logger;
    public static Main instance;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        instance = this;

        try {
            LukkitPluginLoader.tmpDir = Files.createTempDirectory("lukkit_");
        } catch (IOException e) { e.printStackTrace(); }

        if (getConfig().get("update-checker").equals(true))
            UpdateChecker.checkForUpdates(getDescription().getVersion());

        LuaEnvironment.init(this.getConfig().getBoolean("lua-debug"));
    }

    @Override
    public void onDisable() {
        LuaEnvironment.shutdown();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().startsWith("lukkit")) {
            if (args.length != 0) {
                String cmd = args[0];
                args = (String[]) ArrayUtils.remove(args, 0);

                if (cmd.equalsIgnoreCase("help")) {
                    sender.sendMessage(getHelpMessage());
                } else if (cmd.equalsIgnoreCase("reload")) {
                    if (!isEmptyArgs(args)) {

                    } else {
                        LuaEnvironment.shutdown();
                        LuaEnvironment.init(this.getConfig().getBoolean("lua-debug"));
                    }
                }
            } else {
                sender.sendMessage(getHelpMessage());
            }
        }
        return false;
    }

    private static boolean isEmptyArgs(String[] args) { return (args.length == 0 || args[0].equals("")); }

    private String getHelpMessage() {
        String[] message = {
                ChatColor.GREEN + "Lukkit commands:\n",
                ChatColor.YELLOW + "  • \"/lukkit\" - The root command for all commands (shows this message)\n",
                "  • \"/lukkit help\" - Display this message\n",
                "  • \"/lukkit reload [plugin]\" - Reloads all plugins or the specified plugin\n",
                "  • \"/lukkit run (lua code)\" - Runs the specified code as command arguments\n",
                "  • \"/lukkit plugins\" - Lists all enabled plugins\n",
                "  • \"/lukkit stacktrace\" - Gets the last error as a stacktrace\n",
                "  • \"/lukkit simple-error\" - Returns the last error as a one line message"
        };
        return Arrays.toString(message);
    }
}
