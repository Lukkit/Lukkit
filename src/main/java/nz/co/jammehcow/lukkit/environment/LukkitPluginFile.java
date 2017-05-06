package nz.co.jammehcow.lukkit.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Wrapper for a .lkt file or directory.
 *
 * @author jammehcow
 */
public class LukkitPluginFile {
    private File file;
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
                ZipFile zipFile = new ZipFile(this.file, ZipFile.OPEN_READ);
                ArrayList<InputStream> zipContentStreams = new ArrayList<>();

                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    zipContentStreams.add(zipFile.getInputStream(entry));
                }

                zipContentStreams.forEach((f) -> {
                    try {
                        Files.createTempFile(LukkitPluginLoader.tmpDir, null, null);
                    } catch (IOException e) { e.printStackTrace(); }
                });
            } catch (IOException e) { e.printStackTrace(); }
        } else {
            // TODO: is directory
        }
    }

    private File getReadableDirectory(File pluginFile) {
        if (pluginFile.isDirectory()) {
            return pluginFile;
        } else {
            File tempDir = LukkitPluginLoader.tmpDir.toFile();
            try {
                ZipFile zip = new ZipFile(file);

                // TODO
            } catch (IOException e) { e.printStackTrace(); }
        }

        return null;
    }

    /**
     * Gets the main Lua file (main.lua)
     *
     * @return the main
     */
    public File getMain() {
        return new File(this.file.getAbsolutePath() + File.separator + "main.lua");
    }

    /**
     * Gets the plugin.yml equivalent for Lukkit plugins (plugin.yml)
     *
     * @return the config
     */
    public File getPluginYML() {
        return new File(this.file.getAbsolutePath() + File.separator + "plugin.yml");
    }

    /**
     * Gets the config (config.yml).
     *
     * @return the config
     */
    public File getDefaultConfig() {
        return new File(this.file.getAbsolutePath() + File.separator + "config.yml");
    }

    /**
     * Gets a packaged resource from the plugin file and exports it to the plugin's datafolder.
     *
     * @param resource the name of the resource
     * @return the resource as a File
     * @throws FileNotFoundException thrown when the specified resource isn't found
     */
    public File getResource(String resource) throws FileNotFoundException {
        File resourceFile = new File(this.file.getAbsolutePath() + File.separator + resource);

        if (resourceFile.exists()) {
            return resourceFile;
        } else {
            throw new FileNotFoundException("The specified resource could not be found in " + this.file.getAbsolutePath() + ".");
        }
    }
}
