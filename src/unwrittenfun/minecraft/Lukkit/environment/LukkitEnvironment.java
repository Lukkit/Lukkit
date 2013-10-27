package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.JsePlatform;
import unwrittenfun.minecraft.lukkit.Lukkit;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitEnvironment {
    public static Globals _G;

    public static void loadEnvironment() {
        _G = JsePlatform.standardGlobals();

        LuaC.install();
        _G.compiler = LuaC.instance;

        loadLuaLibs();
    }

    public static void loadLuaLibs() {
        String globalsCode = Lukkit.convertStreamToString(Lukkit.class.getResourceAsStream("libs/globals.lua"));
        runString(globalsCode);

        String functionsCode = Lukkit.convertStreamToString(Lukkit.class.getResourceAsStream("libs/functions.lua"));
        runString(functionsCode);
    }

    public static LuaValue runString(String code) {
        return _G.loadString(code, code).call();
    }
}
