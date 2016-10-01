package unwrittenfun.minecraft.lukkit.environment;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.HandlerList;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.JsePlatform;
import unwrittenfun.minecraft.lukkit.Lukkit;
import unwrittenfun.minecraft.lukkit.environment.events.LukkitEventObject;
import unwrittenfun.minecraft.lukkit.environment.events.LukkitEvents;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitEnvironment {
    public static Globals _G;
    public static String lastError;
    public static ArrayList<LukkitCommand> commands = new ArrayList<LukkitCommand>();
    public static LukkitPluginLoader pluginLoader = new LukkitPluginLoader();

    public static void loadEnvironment() {
        _G = JsePlatform.standardGlobals();

        LuaC.install(_G);
        _G.compiler = LuaC.instance;

        for (LukkitCommand cmd : commands)
            unregisterCommand(cmd, false);
        commands = new ArrayList<LukkitCommand>();

        pluginLoader.disableAll();

        LukkitEvents.eventMap = new HashMap<String, ArrayList<LuaFunction>>();
        HandlerList.unregisterAll(Lukkit.instance);
        LukkitEvents.registerEvents();
        _G.set("events", new LukkitEventObject());
        _G.set("lukkit", new LukkitObject());

        loadLuaLibs();
    }

    public static void loadLuaLibs() {
        loadResource("libs/functions.lua", "functions");
        loadResource("libs/globalVariables.lua", "globals");
        loadResource("libs/itemHelpers.lua", "itemHelpers");
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    public static void loadPlugins() {
        File data = Lukkit.instance.getDataFolder();
        if (!data.exists()) data.mkdir();

        for (File pluginFile : data.listFiles()) {
            if (pluginFile.isFile() && pluginFile.getName().toLowerCase(Locale.ENGLISH).endsWith(".lua")) {
                try {
                    LukkitEnvironment.loadFile(pluginFile);
                } catch (LuaError e) {
                    LukkitEnvironment.lastError = e.getMessage();
                    Lukkit.logger.warning(e.getMessage());
                }
            } else if (pluginFile.isDirectory()) {
                File main = new File(pluginFile, "main.lua");
                if (main.exists()) {
                    LukkitEnvironment.loadFile(main);
                } else {
                    Lukkit.logger.warning("No main.lua found for plugin " + pluginFile.getName());
                }
            }
        }
    }

    public static LuaValue runString(String code) {
        try {
            return _G.load(code, code).call();
        }  catch (LuaError e) {
            lastError = e.getMessage();
        }

        return LuaValue.valueOf("ERROR");
    }

    public static void loadFile(File pluginFile) {
        LuaValue chunk = LukkitEnvironment._G.loadfile(pluginFile.getAbsolutePath());
        if (chunk.isnil()) {
            throw new LuaError(chunk.tojstring(2));
        }
        chunk.call(pluginFile.getPath());
    }

    public static void loadResource(String resourceName, String chunkName) {
        Reader fileReader = new InputStreamReader(Lukkit.class.getResourceAsStream(resourceName));
        LuaValue chunk = LukkitEnvironment._G.load(fileReader, chunkName);
        if (chunk.isnil()) {
            throw new LuaError(chunk.tojstring(2));
        }
        chunk.call(chunkName);
    }

    private static Object getPrivateField(Object object, String field) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    public static void registerCommand(LukkitCommand cmd) {
        try {
            commands.add(cmd);
            Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            commandMap.register("lukkit", cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregisterCommand(LukkitCommand cmd, boolean remove) {
        try {
            if (remove) commands.remove(cmd);
            Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            Object map = getPrivateField(commandMap, "knownCommands");
            @SuppressWarnings("unchecked")
            HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
            knownCommands.remove(cmd.getName());
            for (String alias : cmd.getAliases()) {
                if (knownCommands.containsKey(alias)) {
                    knownCommands.remove(alias);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
