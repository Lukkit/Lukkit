package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import com.google.gson.JsonObject;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;

/**
 * @author jammehcow
 */

public class JsonStorage extends StorageObject {
    private JsonObject object = new JsonObject();

    public JsonStorage(LukkitPlugin plugin, String path) {
        super(plugin, path, Storage.JSON);
    }

    @Override
    public boolean setDefaultValue(String path, Object value) {
        return false;
    }

    @Override
    public void setValue(String path, Object value) {

    }

    @Override
    public Object getValue(String path) {
        return null;
    }

    @Override
    public void save() {

    }
}
