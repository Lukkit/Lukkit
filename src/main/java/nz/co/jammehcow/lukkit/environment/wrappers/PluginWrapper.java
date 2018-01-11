package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import nz.co.jammehcow.lukkit.environment.plugin.commands.LukkitCommand;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.JsonStorage;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.StorageObject;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.YamlStorage;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.HashMap;

/**
 * The plugin wrapper providing access to plugin functions.
 *
 * @author jammehcow
 */
public class PluginWrapper extends LuaTable {
    private LukkitPlugin plugin;
    private HashMap<String, StorageObject> cachedObjects = new HashMap<>(); // String path, StorageObject object

    /**
     * Creates a new plugin wrapper.
     *
     * @param plugin the Lukkit plugin
     */
    public PluginWrapper(final LukkitPlugin plugin) {
        this.plugin = plugin;

        set("onLoad", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setLoadCB(callback.checkfunction());
                } else {
                    throw new LukkitPluginException("There was an issue registering the onLoad callback - was provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("onEnable", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setEnableCB(callback.checkfunction());
                } else {
                    throw new LukkitPluginException("There was an issue registering the onEnable callback - was provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("onDisable", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue callback) {
                if (callback.isfunction()) {
                    plugin.setDisableCB(callback.checkfunction());
                } else {
                    throw new LukkitPluginException("There was an issue registering the onDisable callback - was provided a " + callback.typename() + " instead of a function.");
                }
                return LuaValue.NIL;
            }
        });

        set("addCommand", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                if (!arg1.istable() || !arg2.isfunction())
                    throw new LukkitPluginException("There was an issue registering a command. Check that the command registration conforms to the layout here: ");

                LukkitCommand command;
                LuaTable cmd = arg1.checktable();
                LuaFunction function = arg2.checkfunction();

                String cmdName = cmd.get("name").checkjstring();

                String cmdDescription = "";
                if (cmd.get("description") != LuaValue.NIL)
                    cmdDescription = cmd.get("description").checkjstring();

                String cmdUsage = "";
                if (cmd.get("usage") != LuaValue.NIL)
                    cmdUsage = cmd.get("usage").checkjstring();


                command = new LukkitCommand(plugin, function, cmdName, cmdDescription, cmdUsage);
                plugin.registerCommand(command);
                return CoerceJavaToLua.coerce(command);
            }
        });

        set("registerEvent", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                if (Main.events.containsKey(arg1.checkjstring())) {
                    plugin.registerEvent(Main.events.get(arg1.tojstring()), arg2.checkfunction());
                    return LuaValue.NIL;
                }

                throw new LukkitPluginException("There was an issue trying to register the event " + arg1.tostring() + ". Is it a valid event name and properly capitalized?");
            }
        });

        set("getServer", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(plugin.getServer());
            }
        });

        set("setNaggable", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                plugin.setNaggable(arg.checkboolean());
                return LuaValue.NIL;
            }
        });

        set("isNaggable", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(plugin.isNaggable());
            }
        });

        set("exportResource", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue path, LuaValue replace) {
                if (!plugin.getPluginFile().resourceExists(path.checkjstring())) return LuaValue.FALSE;

                plugin.saveResource(path.checkjstring(), replace.checkboolean());
                return LuaValue.TRUE;
            }
        });

        set("getStorageObject", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue path) {
                StorageObject.Storage type;
                String stringPath = (path.checkjstring().startsWith("/")) ?
                        path.checkjstring() : "/" + path.checkjstring();

                if (stringPath.toLowerCase().endsWith(".json")) {
                    type = StorageObject.Storage.JSON;
                } else if (stringPath.toLowerCase().endsWith(".yml")) {
                    type = StorageObject.Storage.YAML;
                } else {
                    LuaError error = new LuaError("The provided file for a storage object was not a JSON or YAML file.");
                    LuaEnvironment.addError(error);
                    throw error;
                }


                if (!cachedObjects.containsKey(stringPath)) {
                    StorageObject obj = (type == StorageObject.Storage.JSON) ? new JsonStorage(plugin, stringPath) : new YamlStorage(plugin, stringPath);
                    cachedObjects.put(stringPath, obj);
                }

                return CoerceJavaToLua.coerce(cachedObjects.get(stringPath));
            }
        });
    }

    @Override
    public String typename() {
        return ObjectType.WRAPPER.name;
    }

    @Override
    public int type() {
        return ObjectType.WRAPPER.type;
    }
}
