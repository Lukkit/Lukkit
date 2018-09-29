package nz.co.jammehcow.lukkit.environment.plugin;

import org.bukkit.plugin.InvalidDescriptionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Wrapper for a .lkt file or directory.
 *
 * @author jammehcow
 */
public class LukkitPluginFile {
    private File file;
    private ZipFile zipFile;
    private boolean isDevPlugin;

    /**
     * Instantiates a new Lukkit plugin file
     *
     * @param plugin the plugin file (.lkt)
     */
    public LukkitPluginFile(File plugin) {
        this.file = plugin;
        this.isDevPlugin = this.file.isDirectory();

        if (!this.isDevPlugin) {
            try {
                this.zipFile = new ZipFile(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the plugin.yml equivalent for Lukkit plugins (plugin.yml)
     *
     * @return the config
     */
    public InputStream getPluginYML() throws InvalidDescriptionException {
        InputStream pluginYml = this.getResource("plugin.yml");
        if (pluginYml == null) {
            throw new InvalidDescriptionException("The description provided was missing.");
        }
        return pluginYml;
    }

    /**
     * Gets the default config (config.yml).
     *
     * @return the config
     */
    public InputStream getDefaultConfig() {
        return this.getResource("config.yml");
    }

    /**
     * Gets a packaged resource from the plugin file and exports it to the plugin's datafolder.
     *
     * @param resource the name of the resource
     * @return the resource as a File or null if not found
     */
    public InputStream getResource(String resource) {
        try {
            if (this.isDevPlugin) {
                File file = new File(this.file.getAbsolutePath() + File.separator + resource);
                if (!file.exists()) {
                    return null;
                }
                return new FileInputStream(file);
            }
            ZipEntry entry = this.zipFile.getEntry(resource);
            if (entry == null) {
                return null;
            }
            return this.zipFile.getInputStream(entry);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Is the plugin a dev plugin (in a directory rather than tied up with a pink bow).
     *
     * @return if the plugin is a indev plugin.
     */
    public boolean isDevPlugin() {
        return this.isDevPlugin;
    }

    /**
     * Checks if the resource at the given path exists.
     *
     * @param path the internal resource path
     * @return if the resource exists
     */
    public boolean resourceExists(String path) {
        return this.getResource(path) != null;
    }

    /**
     * Gets the plugin file path.
     *
     * @return the path
     */
    public String getPath() {
        return this.file.getPath();
    }
}
