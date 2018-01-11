package nz.co.jammehcow.lukkit.environment.plugin.commands;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author AL_1
 */

public class CommandEvent extends LuaTable {

    public CommandEvent(CommandSender sender, String command, String... args) {


        this.set("isPlayerSender", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return sender instanceof Player ? TRUE : FALSE;
            }
        });

        this.set("isConsoleSender", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return sender instanceof ConsoleCommandSender ? TRUE : FALSE;
            }
        });

        this.set("isBlockSender", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return sender instanceof BlockCommandSender ? TRUE : FALSE;
            }
        });

        this.set("isEntitySender", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return sender instanceof Entity ? TRUE : FALSE;
            }
        });

        this.set("getSender", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(sender);
            }
        });

        this.set("getArgs", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(args);
            }
        });

        this.set("getCommand", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(command);
            }
        });

    }

    @Override
    public String typename() {
        return LuaEnvironment.ObjectType.COMMAND_EVENT.name;
    }

    @Override
    public int type() {
        return LuaEnvironment.ObjectType.COMMAND_EVENT.type;
    }
}
