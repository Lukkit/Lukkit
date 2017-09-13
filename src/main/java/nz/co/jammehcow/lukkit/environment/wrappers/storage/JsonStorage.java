package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.exception.StorageObjectException;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.wrappers.storage.compat.LuaJsonElement;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;

import java.io.*;
import java.util.Optional;
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
    protected LuaBoolean exists(String path) {
        // TODO
        return LuaValue.FALSE;
    }

    @Override
    public LuaBoolean setDefaultValue(LuaString path, LuaValue value) throws StorageObjectException {
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
    public void setValue(LuaString path, LuaValue value) throws StorageObjectException {
        // TODO
    }

    @Override
    public LuaValue getValue(LuaString path) throws StorageObjectException {
        Optional<JsonElement> possibleMatch = this.getAtPath(path.checkjstring());

        if (possibleMatch.isPresent()) {
            LuaJsonElement element = new LuaJsonElement(possibleMatch.get());

            System.out.println(element.getElement());

            return element.getElement();
        } else {
            StorageObjectException error = new StorageObjectException("Tried to traverse over an JSON object/array where there was none. Try using StorageObject:exists(\"path\")");
            LuaEnvironment.addError(error);
            throw error;
        }
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

    private Optional<JsonElement> getAtPath(String path) {
        JsonElement current = this.object;
        String splitPath[] = path.split("\\.");

        Pattern arrayIndexPattern = Pattern.compile("/\\[(\\d*)]$/");

        for (int i = 0; i < splitPath.length; i++) {
            System.out.println("Current block: " + splitPath[i]);
            if (current.isJsonObject()) {
                System.out.println("Object");
                current = current.getAsJsonObject().get(splitPath[i]);
            } else if (current.isJsonArray()) {
                System.out.println("Array");
                Matcher match = arrayIndexPattern.matcher(splitPath[i]);
                // TODO: safety check, rather than default array index
                int index = (match.find()) ? Integer.parseInt(match.group(1)) : 0;

                JsonElement jsonElement = current.getAsJsonArray().get(index);
                current = jsonElement.getAsJsonObject().get(splitPath[i]);
            } else {
                if (i != splitPath.length - 1) {
                    System.out.println(current.toString());
                    return Optional.empty();
                }
            }
        }

        return Optional.of(current);
    }
}
