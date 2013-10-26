package unwrittenfun.minecraft.lukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import unwrittenfun.minecraft.lukkit.environment.LukkitEnvironment;
import unwrittenfun.minecraft.lukkit.objects.ServerObject;

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

        loadEnvironment();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lukkit") && args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                loadEnvironment();
                sender.sendMessage("Lukkit environment and plugins reloaded.");
                return true;
            } else if (args[0].equalsIgnoreCase("run") && args.length > 1) {
                String code = "";
                for (int i = 1; i < args.length; i++) {
                    code += args[i] + " ";
                }
                System.out.println(code);
                LukkitEnvironment._G.loadString(code, code).call();
                return true;
            }
        }
        return false;
    }

    public void loadEnvironment() {
        LukkitEnvironment.loadEnvironment();
        registerObjects();

        loadPlugins();
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

    public void registerObjects() {
        LukkitEnvironment.registerObject(new ServerObject());
    }
}
