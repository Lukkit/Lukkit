package nz.co.jammehcow.lukkit.environment.wrappers.storage;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author jammehcow
 */

public abstract class StorageObject {
    enum Storage {
        JSON("json"),
        YAML("yaml");

        private final String type;
        Storage(String type) { this.type = type; }
    }

    private File storageFile;
    private LukkitPlugin plugin;
    private boolean autosave = true;
    private Storage type;

    public StorageObject(LukkitPlugin plugin, String path, Storage type) {
        this.type = type;
        this.plugin = plugin;
        this.storageFile = new File(this.plugin.getDataFolder().getAbsolutePath() + File.separator + path);

        if (!this.storageFile.exists()) {
            try {
                Files.createFile(this.storageFile.toPath());
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public String getType() {
        return this.type.type;
    }

    public abstract boolean setDefaultValue(String path, Object value);
    public abstract boolean setDefaultValue(String path, String value);
    public abstract boolean setDefaultValue(String path, int value);
    public abstract boolean setDefaultValue(String path, boolean value);
    public abstract boolean setDefaultValue(String path, double value);
    public abstract boolean setDefaultValue(String path, long value);

    public abstract void setValue(String path, Object value);
    public abstract void setValue(String path, String value);
    public abstract void setValue(String path, int value);
    public abstract void setValue(String path, boolean value);
    public abstract void setValue(String path, double value);
    public abstract void setValue(String path, long value);

    public abstract Object getValue(String path);

    public abstract void save();

    public void setAutosave(boolean autosave) {
        this.autosave = autosave;
    }

    public boolean isAutosaving() {
        return this.autosave;
    }

    public String getFilePath() {
        return this.storageFile.getAbsolutePath();
    }

    public LukkitPlugin getPlugin() {
        return this.plugin;
    }

    public File getStorageFile() {
        return this.storageFile;
    }
}
