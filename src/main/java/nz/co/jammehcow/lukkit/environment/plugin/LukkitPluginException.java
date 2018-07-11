package nz.co.jammehcow.lukkit.environment.plugin;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

/**
 * The type Lukkit plugin exception.
 *
 * @author jammehcow
 */
public class LukkitPluginException extends LuaError {
    /**
     * Instantiates a new Lukkit plugin exception.
     *
     * @param cause the cause
     */
    public LukkitPluginException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Lukkit plugin exception.
     *
     * @param message the message
     */
    public LukkitPluginException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Lukkit plugin exception.
     *
     * @param message the message
     * @param level   the level
     */
    public LukkitPluginException(String message, int level) {
        super(message, level);
    }

    /**
     * Instantiates a new Lukkit plugin exception.
     *
     * @param message_object the message object
     */
    public LukkitPluginException(LuaValue message_object) {
        super(message_object);
    }
}
