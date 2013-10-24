package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitMethod extends VarArgFunction {
    public int index;
    public String name;
    public ILukkitObject lukkitObject;

    public LukkitMethod(int index, String name, ILukkitObject lukkitObject) {
        this.index = index;
        this.name = name;
        this.lukkitObject = lukkitObject;
    }

    @Override
    public Varargs invoke(Varargs args) {
        return lukkitObject.doMethod(index, name, args);
    }
}
