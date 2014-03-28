package unwrittenfun.minecraft.lukkit.environment;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitEnvironment {
    public static Globals _G;
    public static String lastError;
    public static ArrayList<LukkitCommand> commands = new ArrayList<LukkitCommand>();

    public static void loadEnvironment() {
        _G = JsePlatform.standardGlobals();

        LuaC.install(_G);
        _G.compiler = LuaC.instance;

        for (LukkitCommand cmd : commands)
            unregisterCommand(cmd, false);
        commands = new ArrayList<LukkitCommand>();

        LukkitEvents.eventMap = new HashMap<String, ArrayList<LuaFunction>>();
        HandlerList.unregisterAll(Lukkit.instance);
        LukkitEvents.registerEvents();
        _G.set("events", new LukkitEventObject());
        _G.set("lukkit", new LukkitObject());

        loadLuaLibs();
    }

    public static void loadLuaLibs() {
        String globalsCode = Lukkit.convertStreamToString(Lukkit.class.getResourceAsStream("libs/globalVariables.lua"));
        runString(globalsCode);

        String functionsCode = Lukkit.convertStreamToString(Lukkit.class.getResourceAsStream("libs/functions.lua"));
        runString(functionsCode);
    }

    public static LuaValue runString(String code) {
        try {
            return _G.load(code, code).call();
        }  catch (LuaError e) {
            lastError = e.getMessage();
        }

        return LuaValue.valueOf("ERROR");
    }

    private static Object getPrivateField(Object object, String field) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    public static void unregisterCommand(LukkitCommand cmd, boolean removeFromCommands) {
        try {
            if (removeFromCommands)
                commands.remove(cmd);
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
}
