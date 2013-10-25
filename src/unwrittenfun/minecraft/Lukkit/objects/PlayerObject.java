package unwrittenfun.minecraft.lukkit.objects;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import unwrittenfun.minecraft.lukkit.bridge.LuaBridge;
import unwrittenfun.minecraft.lukkit.environment.ILukkitObject;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class PlayerObject implements ILukkitObject {
    public Player player;

    public PlayerObject(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public String[] getMethods() {
        return new String[] {
                "listMethods",
                "getName"
        };
    }

    @Override
    public Varargs doMethod(int methodIndex, String methodName, Varargs args) {
        switch (methodIndex) {
            case 1: // getName
                return LuaBridge.toLuaValue(player.getName());
        }

        return LuaValue.NIL;
    }
}
