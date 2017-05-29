package nz.co.jammehcow.lukkit.environment;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginLoader;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * @author jammehcow
 */

public class LuaEnvironment {
    public static Globals globals;

    public static void init(boolean isDebug) {
        globals = (isDebug) ? JsePlatform.debugGlobals() : JsePlatform.standardGlobals();

        //loader.loadAllPlugins();
    }

    public static void shutdown() {
        // Stub
    }
}
