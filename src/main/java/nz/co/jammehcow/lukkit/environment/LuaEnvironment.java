package nz.co.jammehcow.lukkit.environment;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
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
    // TODO: make a better error capturing system. 2.1
    public static LuaError lastError;

    public static void init(boolean debug) {
        isDebug = debug;
    }

    public static Globals getNewGlobals(LukkitPlugin plugin) {
        Globals g = (isDebug) ? JsePlatform.debugGlobals() : JsePlatform.standardGlobals();
        g.set("__lukkitpackages__", new LuaTable());
        g.set("require_local", new OneArgFunction() {
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public LuaValue call(LuaValue arg) {
                String path = arg.checkjstring();

                // It's fine to append your path with .lua as it follows Lua standards.
                if (path.startsWith("/")) path.replaceFirst("/", "");
                if (!path.endsWith(".lua")) path = path + ".lua";

                // Load the script if it's already in memory for this plugin.
                LuaValue possiblyLoadedScript = g.get("__lukkitpackages__").checktable().get(path);
                if (possiblyLoadedScript != null) return possiblyLoadedScript;

                InputStream is = plugin.getResource(path);
                if (is != null) try {
                    try {
                        LuaValue calledScript = g.load(new InputStreamReader(is, "UTF-8"), path.replace("/", ".")).call();
                        g.get("__lukkitpackages__").checktable().set(path, calledScript);
                        return calledScript;
                    } catch (LukkitPluginException e) {
                        e.printStackTrace();
                        lastError = e;
                    }
                } catch (UnsupportedEncodingException e) { e.printStackTrace(); }

                throw new LukkitPluginException("Requested Lua file at " + path + " but it does not exist.");
            }
        });
        return g;
    }
}
