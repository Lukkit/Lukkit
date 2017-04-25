package nz.co.jammehcow.lukkit.environment;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * @author jammehcow
 */

public class LuaEnvironment {
    public static Globals globals;

    public static void init(boolean isDebug) {
        globals = (isDebug) ? JsePlatform.debugGlobals() : JsePlatform.standardGlobals();

        LukkitPluginLoader.loadAllPlugins();
    }

    public static void shutdown() {
        // stub
    }
}
