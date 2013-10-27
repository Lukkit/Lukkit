package unwrittenfun.minecraft.lukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import unwrittenfun.minecraft.lukkit.environment.LukkitEnvironment;

import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class Lukkit extends JavaPlugin {
    public static Lukkit instance;
    public static Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        LukkitEnvironment.loadEnvironment();
        loadPlugins();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lukkit") && args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                LukkitEnvironment.loadEnvironment();
                loadPlugins();
                sender.sendMessage("Lukkit environment and plugins reloaded.");
                return true;
            } else if (args[0].equalsIgnoreCase("run") && args.length > 1) {
                String code = "";
                for (int i = 1; i < args.length; i++) {
                    code += args[i] + " ";
                }
                LukkitEnvironment.runString(code);
                return true;
            } else if (args[0].equalsIgnoreCase("resetenv")) {
                LukkitEnvironment.loadEnvironment();
                sender.sendMessage("Lukkit environment reset.");
            }
        }

        return false;
    }

    public void loadPlugins() {
        File data = getDataFolder();
        if (!data.exists()) data.mkdir();

        for (File pluginFile : data.listFiles()) {
            if (pluginFile.isFile() && pluginFile.getName().toLowerCase(Locale.ENGLISH).endsWith(".lua")) {
                try {
                    LuaValue chunk = LukkitEnvironment._G.loadFile(pluginFile.getAbsolutePath());
                    if (chunk.isnil()) {
                        throw new LuaError(chunk.tojstring(2));
                    }
                    chunk.call(pluginFile.getPath());
                } catch (LuaError e) {
                    logger.warning(e.getMessage());
                }
            }
        }
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
