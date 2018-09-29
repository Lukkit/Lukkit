package nz.co.jammehcow.lukkit.environment.wrappers.storage.compat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Map;

/**
 * @author jammehcow
 */

public class LuaJsonElement {
    private LuaValue element;

    public LuaJsonElement(JsonElement jsonElement) {
        this.element = this.getValueFromElement(jsonElement);
    }

    private LuaTable getTableFromElement(JsonElement element) {
        LuaTable finalTable = new LuaTable();

        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                // Add one to i to match Lua array indexing standards
                finalTable.set(i + 1, this.getValueFromElement(array.get(i)));
            }
        } else {
            JsonObject obj = element.getAsJsonObject();

            for (Map.Entry<String, JsonElement> objectEntry : obj.entrySet()) {
                finalTable.set(objectEntry.getKey(), this.getValueFromElement(objectEntry.getValue()));
            }
        }

        return finalTable;
    }

    private LuaValue getValueFromElement(JsonElement element) {
        if (element.isJsonArray() || element.isJsonObject()) {
            return this.getTableFromElement(element);
        } else if (element.isJsonNull()) {
            return LuaValue.NIL;
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitiveValue = element.getAsJsonPrimitive();

            if (primitiveValue.isBoolean()) {
                return LuaValue.valueOf(primitiveValue.getAsBoolean());
            } else if (primitiveValue.isString()) {
                return LuaValue.valueOf(primitiveValue.getAsString());
            } else if (primitiveValue.isNumber()) {
                Number numberValue = primitiveValue.getAsNumber();

                if (numberValue instanceof Double) return LuaValue.valueOf(numberValue.doubleValue());
                else if (numberValue instanceof Integer) return LuaValue.valueOf(numberValue.intValue());
                else if (numberValue instanceof Short) return LuaValue.valueOf(numberValue.shortValue());
                else if (numberValue instanceof Long) return LuaValue.valueOf(numberValue.longValue());
                else if (numberValue instanceof Float) return LuaValue.valueOf(numberValue.floatValue());
                else if (numberValue instanceof Byte) return LuaValue.valueOf(numberValue.byteValue());
            }
        } else {
            LuaError error = new LuaError("A LuaJsonElement object was passed an unsupported value other than that " +
                    "supported by LuaJ. Value: " + element.toString());
            LuaEnvironment.addError(error);
            error.printStackTrace();
        }

        return LuaValue.NIL;
    }

    public LuaValue getElement() {
        return element;
    }
}
