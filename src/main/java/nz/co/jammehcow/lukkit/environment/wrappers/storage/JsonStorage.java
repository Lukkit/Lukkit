package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.compat.LuaJsonElement;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String[] objects = path.checkjstring().split(".");
        JsonElement currentElement = this.object;

        for (int i = 1; i < objects.length; i++) {


            if (objects[i].contains("[")) {

            } else {
                currentElement = currentElement.getAsJsonObject().getAsJsonObject();
            }
        }

        return LuaValue.TRUE;
    }

    @Override
    public void setValue(LuaString path, LuaValue value) {

    }

    @Override
    public LuaValue getValue(LuaString path) {
        return new LuaJsonElement(this.getAtPath(path.checkjstring())).getElement();
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

    private JsonElement getAtPath(String path) {
        JsonElement current = this.object;
        String splitPath[] = path.split(".");

        Pattern arrayIndexPattern = Pattern.compile("/\\[(\\d*)]$/");

        for (String pathSection : splitPath) {
            System.out.println("Current block: " + path);
            if (current instanceof JsonObject) {
                System.out.println("Object");
                current = current.getAsJsonObject().get(pathSection);
            } else if (current instanceof JsonArray) {
                System.out.println("Array");
                Matcher match = arrayIndexPattern.matcher(pathSection);
                int index = (match.find()) ? Integer.parseInt(match.group(1)) : 0;

                JsonElement jsonElement = current.getAsJsonArray().get(index);
                current = jsonElement.getAsJsonObject().get(pathSection);
            } else {
                return null;
            }
        }

        return current;
    }
}
