package nz.co.jammehcow.lukkit;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.LukkitPluginLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
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
            if (args.length == 0) {

            }
        }
        return false;
    }
}
