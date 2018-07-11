package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.logging.Logger;

/**
 * @author jammehcow
 */

public class LoggerWrapper extends LuaTable {
    private LukkitPlugin plugin;
    private Logger logger;

    public LoggerWrapper(final LukkitPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        set("info", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                logger.info(arg.checkjstring());
                return LuaValue.NIL;
            }
        });

        set("warn", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                logger.warning(arg.checkjstring());
                return LuaValue.NIL;
            }
        });

        set("severe", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                logger.severe(arg.checkjstring());
                return LuaValue.NIL;
            }
        });

        set("debug", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                logger.fine(arg.checkjstring());
                return LuaValue.NIL;
            }
        });
    }

    @Override
    public String typename() {
        return ObjectType.WRAPPER.name;
    }

    @Override
    public int type() {
        return ObjectType.WRAPPER.type;
    }
}
