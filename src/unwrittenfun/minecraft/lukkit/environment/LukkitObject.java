package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitObject extends LuaTable {

    public LukkitObject() {

        set("addCommand", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
//                if (args.isfunction(4)) {
//                    Bukkit.getHelpMap().addTopic(new CommandHelpTopic(args.tojstring(1), args.tojstring(2), args.tojstring(3)));
//                    Bukkit.getPluginManager().registerEvents(new CommandListener(args.tojstring(1), (LuaFunction) args.arg(4)), Lukkit.instance);
//                }
                LukkitEnvironment.registerCommand(new LukkitCommand(args.tojstring(1), args.tojstring(2), args.tojstring(3), new ArrayList<String>(), args.checkfunction(4)));
                return LuaValue.NIL;
            }
        });

        set("async", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                LukkitAsyncThread thread = new LukkitAsyncThread(args.tojstring(1), args.checkfunction(2));
                thread.start();
                return LuaValue.NIL;
            }
        });
    }
}
