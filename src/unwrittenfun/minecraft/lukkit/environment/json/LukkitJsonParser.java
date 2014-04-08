package unwrittenfun.minecraft.lukkit.environment.json;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitJsonParser {
    private LuaTable luaTable;
    private JSONObject jsonObject;

    public LukkitJsonParser(LuaTable object) {
        luaTable = object;
        jsonObject = new JSONObject();
    }

    @SuppressWarnings({"unchecked", "UnnecessaryBoxing", "BooleanConstructorCall"})
    public String parse() {
        for (LuaValue key : luaTable.keys()) {
            String jKey = key.tojstring();
            LuaValue value = luaTable.get(key);
            if (value.isboolean()) {
                jsonObject.put(jKey, new Boolean(value.toboolean()));
            } else if (value.isint()) {
                jsonObject.put(jKey, new Integer(value.toint()));
            } else if (value.isnumber()) {
                jsonObject.put(jKey, new Double(value.todouble()));
            } else if (value.isstring()) {
                jsonObject.put(jKey, value.tojstring());
            }
        }

        return JSONValue.toJSONString(jsonObject);
    }
}
