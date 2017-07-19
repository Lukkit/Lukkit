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
    public boolean setDefaultValue(String path, String value) {
        return false;
    }

    @Override
    public boolean setDefaultValue(String path, int value) {
        return false;
    }

    @Override
    public boolean setDefaultValue(String path, boolean value) {
        return false;
    }

    @Override
    public boolean setDefaultValue(String path, double value) {
        return false;
    }

    @Override
    public boolean setDefaultValue(String path, long value) {
        return false;
    }

    @Override
    public void setValue(String path, Object value) {

    }

    @Override
    public void setValue(String path, String value) {

    }

    @Override
    public void setValue(String path, int value) {

    }

    @Override
    public void setValue(String path, boolean value) {

    }

    @Override
    public void setValue(String path, double value) {

    }

    @Override
    public void setValue(String path, long value) {

    }

    @Override
    public Object getValue(String path) {
        return null;
    }

    @Override
    public void save() {

    }
}
