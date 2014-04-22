package unwrittenfun.minecraft.lukkit.environment.json;

import org.json.simple.JSONValue;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitJsonParser {
    public static String parseToJSON(LuaTable table) {
        return JSONValue.toJSONString(mapOfTable(table));
    }

    @SuppressWarnings({"unchecked", "UnnecessaryBoxing", "BooleanConstructorCall"})
    private static HashMap mapOfTable(LuaTable luaTable) {
        HashMap map = new HashMap();
        for (LuaValue key : luaTable.keys()) {
            String jKey = key.tojstring();
            LuaValue value = luaTable.get(key);
            if (value.isboolean()) {
                map.put(jKey, new Boolean(value.toboolean()));
            } else if (value.isint()) {
                map.put(jKey, new Integer(value.toint()));
            } else if (value.isnumber()) {
                map.put(jKey, new Double(value.todouble()));
            } else if (value.istable()) {
                map.put(jKey, mapOfTable(value.checktable()));
            } else if (value.isstring()) {
                map.put(jKey, value.tojstring());
            }
        }
        return map;
    }
}
