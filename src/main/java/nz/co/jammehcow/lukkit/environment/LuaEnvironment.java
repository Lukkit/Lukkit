package nz.co.jammehcow.lukkit.environment;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author jammehcow
 */

public class LuaEnvironment {
    private static boolean isDebug;

    public static void init(boolean debug) {
        isDebug = debug;
    }

    public static Globals getNewGlobals(LukkitPlugin plugin) {
        Globals g = (isDebug) ? JsePlatform.debugGlobals() : JsePlatform.standardGlobals();
        g.set("require_local", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                InputStream is = plugin.getResource(arg.checkjstring());

                if (is != null) return g.load(new InputStreamReader(is), plugin.getName() + "-" + arg.tojstring());

                plugin.getLogger().severe("Requested local plugin file " + arg.tojstring() + " but it could not be found in the plugin.");

                return LuaValue.NIL;
            }
        });
        return g;
    }

    public static void shutdown() {
        // Stub
    }
}
