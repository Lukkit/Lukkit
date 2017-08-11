package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.JsonStorage;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.StorageObject;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.YamlStorage;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.HashMap;

/**
 * @author jammehcow
 */

public class StorageWrapper extends LuaTable {
    private LukkitPlugin plugin;
    private HashMap<String, StorageObject> cachedObjects = new HashMap<>(); // String path, StorageObject object

    public StorageWrapper(LukkitPlugin plugin) {
        set("getStorageObject", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue path) {
                StorageObject.Storage type;
                String stringPath = (path.checkjstring().startsWith("/")) ?
                        path.checkjstring() : "/" + path.checkjstring();

                if (stringPath.endsWith(".json")) {
                    type = StorageObject.Storage.JSON;
                } else if (stringPath.endsWith(".yml")) {
                    type = StorageObject.Storage.YAML;
                } else {
                    // TODO
                    throw new LuaError("");
                }


                if (!cachedObjects.containsKey(stringPath)) {
                    StorageObject obj = (type == StorageObject.Storage.JSON) ? new JsonStorage(plugin, stringPath) : new YamlStorage(plugin, stringPath);
                    cachedObjects.put(stringPath, obj);
                }

                return CoerceJavaToLua.coerce(cachedObjects.get(stringPath));
            }
        });
    }
}
