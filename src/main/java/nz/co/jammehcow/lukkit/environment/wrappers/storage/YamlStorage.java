package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

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
    public LuaBoolean setDefaultValue(LuaString path, LuaValue value) {
        if (this.yamlConfiguration.get(path.checkjstring()) == null) {
            this.yamlConfiguration.set(path.checkjstring(), this.getObjectFromLuavalue(value));
            return LuaValue.TRUE;
        }

        return LuaValue.FALSE;
    }

    @Override
    public void setValue(LuaString path, LuaValue value) {
        this.yamlConfiguration.set(path.checkjstring(), this.getObjectFromLuavalue(value));
    }

    @Override
    public LuaValue getValue(LuaString path) {
        return CoerceJavaToLua.coerce(this.yamlConfiguration.get(path.tojstring()));
    }

    @Override
    public void save() {
        this.preSaveCheck();
        try {
            this.yamlConfiguration.save(this.getStorageFile());
        } catch (IOException e) { e.printStackTrace(); }
    }
}
