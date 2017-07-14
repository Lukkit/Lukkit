package nz.co.jammehcow.lukkit.environment;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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

                // TODO: check if script is already loaded and link back

                if (path.startsWith("/")) path.replaceFirst("/", "");

                // It's fine to append your path with .lua as it follows Lua standards.
                if (!path.endsWith(".lua")) path = path + ".lua";

                InputStream is = plugin.getResource(path);

                if (is != null) try {
                    return g.load(new InputStreamReader(is, "UTF-8"), path.replace("/", ".")).call();
                } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
                plugin.getLogger().severe("Requested Lua file at " + path + " but it does not exist.");

                return LuaValue.NIL;
            }
        });
        return g;
    }
}
