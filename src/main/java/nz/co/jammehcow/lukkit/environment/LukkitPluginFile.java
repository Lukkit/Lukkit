package nz.co.jammehcow.lukkit.environment;

import nz.co.jammehcow.lukkit.Main;

import java.io.*;
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
    LukkitPluginFile(File plugin) {
        this.file = plugin;
        this.isDevPlugin = this.file.isDirectory();
    }

    /**
     * Gets the main Lua file (main.lua)
     *
     * @return the main
     */
    InputStream getMain(String main) {
        if (this.isDevPlugin) {
            try {
                return new FileInputStream(new File(this.file + File.separator + main));
            } catch (FileNotFoundException e) {
                Main.instance.getLogger().warning("TODO");
            }
        } else {
            try {
                return this.zipFile.getInputStream(this.zipFile.getEntry("main.lua"));
            } catch (IOException e) {
                Main.instance.getLogger().warning("TODO");
            }
        }

        return null;
    }

    /**
     * Gets the plugin.yml equivalent for Lukkit plugins (plugin.yml)
     *
     * @return the config
     */
    InputStream getPluginYML() {
        try {
            return this.zipFile.getInputStream(this.zipFile.getEntry("plugin.yml"));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Gets the default config (config.yml).
     *
     * @return the config
     */
    InputStream getDefaultConfig() {
        try {
            return this.zipFile.getInputStream(this.zipFile.getEntry("config.yml"));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Gets a packaged resource from the plugin file and exports it to the plugin's datafolder.
     *
     * @param resource the name of the resource
     * @return the resource as a File
     * @throws FileNotFoundException thrown when the specified resource isn't found
     */
    InputStream getResource(String resource) throws FileNotFoundException {
        try {
            return this.zipFile.getInputStream(this.zipFile.getEntry(resource));
        } catch (IOException e) {
            throw new FileNotFoundException("");
        }
    }

    /**
     * Is the plugin a dev plugin (in a directory rather than tied up with a pink box).
     *
     * @return if the plugin is a indev plugin.
     */
    boolean isDevPlugin() {
        return this.isDevPlugin;
    }
}
