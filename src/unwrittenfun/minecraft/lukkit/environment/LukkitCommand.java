package unwrittenfun.minecraft.lukkit.environment;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.List;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 * Maintainer: jammehcow
*/
public class LukkitCommand extends Command {
    private LuaFunction callback;

    public LukkitCommand(String name, String description, String usageMessage, List<String> aliases, LuaFunction _callback) {
        super(name, description, usageMessage, aliases);
        callback = _callback;
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        LuaValue[] luaArgs = new LuaValue[args.length];
        for (int i = 0; i < args.length; i++) {
            luaArgs[i] = LuaValue.valueOf(args[i]);
        }
        callback.call(CoerceJavaToLua.coerce(commandSender), LuaValue.listOf(luaArgs));
        return true;
    }
}
