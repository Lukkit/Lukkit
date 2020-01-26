package net.lukkit.lukkit.sandbox.api;

import net.lukkit.lukkit.sandbox.plugin.LukkitPlugin;

public class Logger {
    private final java.util.logging.Logger logger;

    public Logger(LukkitPlugin plugin) {
        logger = plugin.getLogger();
    }

    public void info(String ...message) {
        logger.info(joinMessage(message));
    }

    public void warning(String ...message) {
        logger.warning(joinMessage(message));
    }

    public void error(String ...message) {
        logger.severe(joinMessage(message));
    }

    public void debug(String ...message) {
        logger.fine(joinMessage(message));
    }

    private static String joinMessage(String[] segments) {
        return String.join(" ", segments);
    }
}
