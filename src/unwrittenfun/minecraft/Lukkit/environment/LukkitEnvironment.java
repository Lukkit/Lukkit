package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.Globals;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.JsePlatform;

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
    }

    public static void registerObject(ILukkitObject lukkitObject) {
        _G.set(lukkitObject.getName(), new LukkitObject(lukkitObject));
    }
}
