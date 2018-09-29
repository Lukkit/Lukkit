package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import nz.co.jammehcow.lukkit.Utilities;
import nz.co.jammehcow.lukkit.environment.exception.StorageObjectException;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author jammehcow
 */

public class JsonStorage extends StorageObject {
    private Gson gson = new GsonBuilder().create();
    private JsonObject object;

    public JsonStorage(LukkitPlugin plugin, String path) {
        super(plugin, path, Storage.JSON);

        try {
            this.object = new JsonParser().parse(new JsonReader(new FileReader(this.getStorageFile()))).getAsJsonObject();
        } catch (FileNotFoundException | IllegalStateException e) {
            this.object = new JsonObject();
        }
    }

    @Override
    protected LuaBoolean exists(String path) {
        return object.has(path) ? LuaValue.TRUE : LuaValue.FALSE;
    }

    @Override
    public LuaBoolean setDefaultValue(LuaString path, LuaValue value) throws StorageObjectException {
        if (object.has(path.checkjstring())) {
            setValue(path, value);
            return LuaValue.TRUE;
        }
        return LuaValue.FALSE;
    }

    @Override
    public void setValue(LuaString path, LuaValue value) throws StorageObjectException {
        object.add(path.checkjstring(), gson.toJsonTree(Utilities.getObjectFromLuavalue(value)));
    }

    @Override
    protected LuaBoolean clearVaule(LuaString path) throws StorageObjectException {
        if (object.has(path.checkjstring())) {
            object.remove(path.checkjstring());
            return LuaValue.TRUE;
        }
        return LuaValue.FALSE;
    }

    @Override
    public LuaValue getValue(LuaString path) throws StorageObjectException {
        return CoerceJavaToLua.coerce(object.get(path.checkjstring()));
    }

    @Override
    public void save() {
        this.preSaveCheck();
        try {
            Writer writer = new FileWriter(this.getStorageFile());
            gson.toJson(this.object, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
