package unwrittenfun.minecraft.lukkit.bridge;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import unwrittenfun.minecraft.lukkit.environment.LukkitObject;
import unwrittenfun.minecraft.lukkit.objects.PlayerObject;

import java.util.List;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LuaBridge {
    public static LuaValue toLuaValue(Player player) {
        return new LukkitObject(new PlayerObject(player));
    }

    public static LuaValue toLuaValue(String jString) {
        return LuaValue.valueOf(jString);
    }

    public static LuaValue toLuaValue(Integer jInt) {
        return LuaValue.valueOf(jInt);
    }

    public static LuaValue toLuaValue(Boolean jBool) {
        return LuaValue.valueOf(jBool);
    }

    public static LuaValue toLuaValue(Long jLong) {
        return LuaValue.valueOf(jLong);
    }

    public static LuaValue toLuaValue(Object jObject) {
        if (jObject instanceof String) return toLuaValue((String) jObject);
        if (jObject instanceof Integer) return toLuaValue((Integer) jObject);
        if (jObject instanceof Player) return toLuaValue((Player) jObject);

        return LuaValue.NIL;
    }

    public static LuaValue toLuaTable(Object[] jArray) {
        LuaValue[] luaList = new LuaValue[jArray.length];
        for (int i = 0; i < jArray.length; i++) {
            luaList[i] = toLuaValue(jArray);
        }
        return LuaValue.listOf(luaList);
    }

    public static LuaValue toLuaTable(List list) {
        return toLuaTable(list.toArray(new Object[list.size()]));
    }
}
