package nz.co.jammehcow.lukkit.environment.plugin;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

/**
 * The Lukkit configuration file - compatible with the PluginLoader interface implementations.
 *
 * @author jammehcow
 */
public class LukkitConfigurationFile extends FileConfiguration {
    public LukkitConfigurationFile(File config) {
        // TODO
    }

    @Override
    public String saveToString() {
        return null;
    }

    @Override
    public void loadFromString(String s) throws InvalidConfigurationException {

    }

    @Override
    protected String buildHeader() {
        return null;
    }
}
