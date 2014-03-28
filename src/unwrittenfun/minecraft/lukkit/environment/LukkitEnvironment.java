package unwrittenfun.minecraft.lukkit.environment;

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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitEnvironment {
    public static Globals _G;
    public static String lastError;

    public static void loadEnvironment() {
        _G = JsePlatform.standardGlobals();

        LuaC.install(_G);
        _G.compiler = LuaC.instance;

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
}
