package nz.co.jammehcow.lukkit.environment;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class LuaEnvironment {
    private static boolean isDebug;

    private static Stack<Exception> errors = new Stack<>();
    static {
        errors.setSize(10);
    }

    /**
     * Helps collate all of the types together and allows easy changing of IDs
     */
    public enum ObjectType {
        Wrapper(100, "Wrapper"),
        StorageObject(101, "StorageObject");

        public final int type;
        public final String name;
        ObjectType(int type, String name) {
            this.type = type;
            this.name = name;
        }
    }

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
                        addError(e);
                    }
                } catch (UnsupportedEncodingException e) { e.printStackTrace(); }

                throw new LukkitPluginException("Requested Lua file at " + path + " but it does not exist.");
            }
        });
        return g;
    }

    public static Optional<Exception> getLastError() {
        return Optional.of(errors.peek());
    }

    public static Optional<Stream<Exception>> getErrors() {
        return (errors.stream().filter(Objects::nonNull).count() == 0) ? Optional.empty() : Optional.of(errors.stream().filter(Objects::nonNull));
    }

    public static void addError(Exception e) {
        errors.push(e);
    }
}
