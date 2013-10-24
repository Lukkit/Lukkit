package unwrittenfun.minecraft.lukkit.objects;

import org.bukkit.Bukkit;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import unwrittenfun.minecraft.lukkit.environment.ILukkitObject;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class ServerObject implements ILukkitObject {
    @Override
    public String getName() {
        return "server";
    }

    @Override
    public String[] getMethods() {
        return new String[] {
                "broadcast"
        };
    }

    @Override
    public Varargs doMethod(int methodIndex, String methodName, Varargs args) {
        switch (methodIndex) {
            case 0: // broadcast
                return LuaValue.valueOf(Bukkit.getServer().broadcastMessage(args.tojstring(1)));
        }

        return LuaValue.NIL;
    }
}
