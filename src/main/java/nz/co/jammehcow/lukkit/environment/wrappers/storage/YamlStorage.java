package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.luaj.vm2.LuaValue;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author jammehcow
 */

public class YamlStorage extends StorageObject {
    private YamlConfiguration yamlConfiguration;

    public YamlStorage(LukkitPlugin plugin, String path) {
        super(plugin, path, Storage.YAML);

        this.yamlConfiguration = new YamlConfiguration();
        try {
            this.yamlConfiguration.load(new FileReader(this.getStorageFile()));
        } catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
    }

    @Override
    public boolean setDefaultValue(String path, Object value) {
        return false;
    }

    @Override
    public void setValue(String path, Object value) {

    }

    @Override
    public Object getValue(String path) {
        return null;
    }

    @Override
    public void save() {

    }
}
