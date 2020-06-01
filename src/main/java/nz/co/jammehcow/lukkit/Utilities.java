package nz.co.jammehcow.lukkit;

import org.bukkit.event.Event;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utilities {

    /**
     * Gets the Java object from a LuaValue.
     *
     * @param value the LuaValue
     * @return the Java object
     */
    public static Object getObjectFromLuavalue(LuaValue value) {
        if (value.istable()) {
            return convertTable(value.checktable());
        } else if (value.isint()) {
            return value.checkint();
        } else if (value.islong()) {
            return value.checklong();
        } else if (value.isnumber()) {
            return value.checkdouble();
        } else if (value.isstring()) {
            return value.checkjstring();
        } else if (value.isboolean()) {
            return value.checkboolean();
        } else if (value.isnil()) {
            return null;
        } else {
            return value.checkuserdata();
        }
    }

    public static boolean isInteger(String str) {
        if (str == null)
            return false;
        int length = str.length();
        if (length == 0)
            return false;
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1)
                return false;
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    public static Object convertTable(LuaTable table) {
        HashMap<Object, Object> returnedMap = new HashMap<>();
        boolean isArray = true;
        LuaValue[] keys = table.keys();

        for (LuaValue k : keys) {
            if (!isInteger(k.tojstring()))
                isArray = false;
            returnedMap.put(k.tojstring(), getObjectFromLuavalue(table.get(k)));
        }

        if (isArray) {
            List<Object> list = new ArrayList<>();
            returnedMap.values().forEach(o -> list.add(o));
            return list;
        }
        return returnedMap;
    }

    public static boolean classIsEvent(Class<?> c) {
        return Event.class.isAssignableFrom(c);
    }

    /**
     * Check that a given classpath is valid
     *
     * @param classPath the full classpath to validate
     * @return whether the classpath is valid
     */
    public static boolean isClassPathValid(String classPath) {
        try {
            Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
