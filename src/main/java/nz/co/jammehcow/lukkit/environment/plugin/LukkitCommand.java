package nz.co.jammehcow.lukkit.environment.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.luaj.vm2.LuaFunction;

/**
 * @author jammehcow
 */

public class LukkitCommand extends Command {
    public LukkitCommand(String command, LuaFunction function) {
        super(command);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
