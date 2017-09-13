package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.exception.StorageObjectException;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

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

        this.set("getType", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (!self.typename().equals(StorageObject.this.typename())) throw new StorageObjectException("Expected call to StorageObject:getType() to be member call, not static call");

                return LuaValue.valueOf(((StorageObject) self.touserdata()).getType().type);
            }
        });

        this.set("exists", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue path) {
                if (!self.typename().equals(StorageObject.this.typename())) throw new StorageObjectException("Expected call to StorageObject:exists() to be member call, not static call");

                return ((StorageObject) self.touserdata()).exists(path.checkjstring());
            }
        });

        this.set("setDefaultValue", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue path, LuaValue value) {
                if (!self.typename().equals(StorageObject.this.typename())) throw new StorageObjectException("Expected call to StorageObject:setDefaultValue() to be member method call, not static call");

                try {
                    return ((StorageObject) self.touserdata()).setDefaultValue(path.checkstring(), value);
                } catch (StorageObjectException e) {
                    return LuaValue.NIL;
                }
            }
        });

        this.set("setValue", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue path, LuaValue value) {
                if (!self.typename().equals(StorageObject.this.typename())) throw new StorageObjectException("Expected call to StorageObject:setValue() to be member method call, not static call");

                try {
                    ((StorageObject) self.touserdata()).setValue(path.checkstring(), value);
                    return LuaValue.TRUE;
                } catch (StorageObjectException e) {
                    return LuaValue.FALSE;
                }
            }
        });

        this.set("getValue", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue self, LuaValue path) {
                if (!self.typename().equals(StorageObject.this.typename())) throw new StorageObjectException("Expected call to StorageObject:getValue() to be member method call, not static call");

                try {
                    return ((StorageObject) self.touserdata()).getValue(path.checkstring());
                } catch (StorageObjectException e) {
                    return LuaValue.NIL;
                }
            }
        });

        this.set("save", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue self) {
                if (!self.typename().equals(StorageObject.this.typename())) throw new StorageObjectException("Expected call to StorageObject:save() to be member method call, not static call");

                ((StorageObject) self.touserdata()).save();
                return LuaValue.NIL;
            }
        });
    }

    @Override
    public String typename() {
        return ObjectType.StorageObject.name;
    }

    @Override
    public int type() {
        return ObjectType.StorageObject.type;
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
     * Checks if the key exists.
     *
     * @return the type
     */
    protected abstract LuaBoolean exists(String path);

    /**
     * Sets a value if it doesn't exist.
     *
     * @param path  the path of the key
     * @param value the value
     * @return true if the value is set, false if not
     */
    protected abstract LuaBoolean setDefaultValue(LuaString path, LuaValue value) throws StorageObjectException;

    /**
     * Sets the value of a key.
     *
     * @param path  the path of the key
     * @param value the value
     */
    protected abstract void setValue(LuaString path, LuaValue value) throws StorageObjectException;

    /**
     * Gets the value of a key.
     *
     * @param path the path of the key
     * @return the Object value
     */
    protected abstract LuaValue getValue(LuaString path) throws StorageObjectException;

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
