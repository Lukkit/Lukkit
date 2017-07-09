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
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public LuaValue call(LuaValue arg) {
                String path = arg.checkjstring();

                if (path.startsWith("/")) {
                    path.replaceFirst("/", "");
                    plugin.getLogger().warning("Please don't prefix a resource path with a forward slash. Lukkit will remove it, but you'll get spammed with this.");
                }

                // It's fine to append your path with .lua as it follows Lua standards.
                if (!path.endsWith(".lua")) path = path + ".lua";

                InputStream is = plugin.getResource(path);

                if (is != null) return g.load(new InputStreamReader(is), path.replace("/", ".")).call();
                plugin.getLogger().severe("Requested Lua file at " + path + " but it does not exist.");

                return LuaValue.NIL;
            }
        });
        return g;
    }
}
