package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * The abstract Storage class.
 *
 * @author jammehcow
 */
public abstract class StorageObject extends LuaTable {
    /**
     * The enum Storage.
     */
    public enum Storage {
        /**
         * JSON storage.
         */
        JSON("json"),
        /**
         * YAML storage.
         */
        YAML("yaml");

        private final String type;
        Storage(String type) { this.type = type; }
    }

    private File storageFile;
    private LukkitPlugin plugin;
    private Storage type;

    /**
     * Instantiates a new StorageObject.
     *
     * @param plugin the lukkit plugin
     * @param path   the file path
     * @param type   the storage type
     */
    public StorageObject(LukkitPlugin plugin, String path, Storage type) {
        this.type = type;
        this.plugin = plugin;
        this.storageFile = new File(this.plugin.getDataFolder().getAbsolutePath() + File.separator + path);

        if (!this.storageFile.exists()) {
            try {
                Files.createFile(this.storageFile.toPath());
            } catch (IOException e) { e.printStackTrace(); }
        }

        this.set("getType", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return LuaValue.valueOf(getType().type);
            }
        });

        this.set("setDefaultValue", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue path, LuaValue value) {
                return setDefaultValue(path.checkstring(), value);
            }
        });

        this.set("setValue", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue path, LuaValue value) {
                setValue(path.checkstring(), value);
                return LuaValue.NIL;
            }
        });

        this.set("getValue", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue path) {
                return getValue(path.checkstring());
            }
        });

        this.set("save", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                save();
                return LuaValue.NIL;
            }
        });
    }

    /**
     * Gets the storage type.
     *
     * @return the type
     */
    private Storage getType() {
        return this.type;
    }

    /**
     * Sets a value if it doesn't exist.
     *
     * @param path  the path of the key
     * @param value the value
     * @return true if the value is set, false if not
     */
    protected abstract LuaBoolean setDefaultValue(LuaString path, LuaValue value);

    /**
     * Sets the value of a key.
     *
     * @param path  the path of the key
     * @param value the value
     */
    protected abstract void setValue(LuaString path, LuaValue value);

    /**
     * Gets the value of a key.
     *
     * @param path the path of the key
     * @return the Object value
     */
    protected abstract LuaValue getValue(LuaString path);

    /**
     * Save the file.
     */
    protected abstract void save();

    /**
     * Gets the absolute path of the storage file.
     *
     * @return the absolute file path
     */
    protected String getFilePath() {
        return this.storageFile.getAbsolutePath();
    }

    /**
     * Gets the associated {@link nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin}.
     *
     * @return the LukkitPlugin
     */
    protected LukkitPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Gets the storage file.
     *
     * @return the storage file
     */
    protected File getStorageFile() {
        return this.storageFile;
    }

    /**
     * Pre save file check.
     */
    protected void preSaveCheck() {
        if (!this.storageFile.exists()) {
            try {
                Files.createFile(this.storageFile.toPath());
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    /**
     * Gets the Java object from a LuaValue.
     *
     * @param value the LuaValue
     * @return the Java object
     */
    protected Object getObjectFromLuavalue(LuaValue value) {
        return (value.istable()) ? this.tableToMap(value.checktable()) : value.checkuserdata();
    }

    private HashMap<Object, Object> tableToMap(LuaTable table) {
        HashMap<Object, Object> returnedMap = new HashMap<>();
        LuaValue[] keys = table.keys();

        for (LuaValue k : keys) {
            returnedMap.put(k.tojstring(), this.getObjectFromLuavalue(table.get(k)));
        }

        return returnedMap;
    }
}
