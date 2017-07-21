package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;

import java.io.*;

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
        } catch (FileNotFoundException e) {
            this.object = new JsonObject();
        }
    }

    @Override
    public LuaBoolean setDefaultValue(LuaString path, LuaValue value) {
        return LuaValue.TRUE;
    }

    @Override
    public void setValue(LuaString path, LuaValue value) {

    }

    @Override
    public LuaValue getValue(LuaString path) {
        return LuaValue.NIL;
    }

    @Override
    public void save() {
        this.preSaveCheck();
        try {
            Writer writer = new FileWriter(this.getStorageFile());
            gson.toJson(this.object, writer);
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
