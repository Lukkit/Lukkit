package unwrittenfun.minecraft.lukkit.environment;

import org.bukkit.Bukkit;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import unwrittenfun.minecraft.lukkit.Lukkit;
import unwrittenfun.minecraft.lukkit.environment.commands.CommandHelpTopic;
import unwrittenfun.minecraft.lukkit.environment.commands.CommandListener;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitObject extends LuaTable {
    public LukkitObject() {
        set("addCommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (args.isfunction(4)) {
                    Bukkit.getHelpMap().addTopic(new CommandHelpTopic(args.tojstring(1), args.tojstring(2), args.tojstring(3)));
                    Bukkit.getPluginManager().registerEvents(new CommandListener(args.tojstring(1), (LuaFunction) args.arg(4)), Lukkit.instance);
                }
                return LuaValue.NIL;
            }
        });
    }
}
