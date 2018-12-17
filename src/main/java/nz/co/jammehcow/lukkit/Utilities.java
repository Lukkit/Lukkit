package nz.co.jammehcow.lukkit;

import org.bukkit.event.Event;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.*;

public class Utilities {

    /**
     * Gets the Java object from a LuaValue.
     *
     * @param value the LuaValue
     * @return the Java object
     */
    public static Object getObjectFromLuaValue(LuaValue value) {
        if (value == null) return null;

        switch (value.type()) {
            case LuaValue.TBOOLEAN:
                return value.toboolean();
            case LuaValue.TNUMBER:
                return value.tonumber();
            case LuaValue.TSTRING:
                return value.toString();
            case LuaValue.TTABLE:
                return convertTable(value.checktable());
            case LuaValue.TFUNCTION:
                return value.checkfunction();
            case LuaValue.TTHREAD:
                return value.checkthread();
            default:
                return value.checkuserdata();
        }
    }

    public static boolean isKeyedTable(LuaTable table) {
        for (LuaValue k : table.keys()) {
            if (k.isstring()) return true;
        }

        return false;
    }

    public static List convertTable(LuaTable table) {
        HashMap<, Object> returnedMap = new HashMap<>();

        returnedMap.putAll(table.);
        returnedMap.put(k.tojstring(), getObjectFromLuaValue(table.get(k)));

        return (isKeyedTable(table)) ? new ArrayList<>(returnedMap.values()) : returnedMap;
    }

    public static boolean classIsEvent(Class<?> c) {
        return Event.class.isAssignableFrom(c);
    }
}
