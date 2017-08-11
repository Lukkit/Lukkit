package nz.co.jammehcow.lukkit.environment.plugin;

import com.avaje.ebean.EbeanServer;
import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.wrappers.*;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.*;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The type Lukkit plugin.
 *
 * @author jammehcow
 */
public class LukkitPlugin implements Plugin {
    private final String name;
    private final LukkitPluginFile pluginFile;
    private final LuaValue pluginMain;
    private LuaFunction loadCB;
    private LuaFunction enableCB;
    private LuaFunction disableCB;
    private File pluginConfig;
    private final LukkitPluginLoader pluginLoader;
    private FileConfiguration config;
    private final PluginDescriptionFile descriptor;
    private final Globals globals;
    private final File dataFolder;
    private boolean enabled = false;
    private boolean naggable = true;
    private final Logger logger;

    private final HashMap<String, LuaFunction> commands = new HashMap<>();
    private final HashMap<String, ArrayList<LuaFunction>> eventCallbacks = new HashMap<>();

    /**
     * Instantiates a new Lukkit plugin.
     *
     * @param loader the loader
     * @param file   the file
     */
    public LukkitPlugin(LukkitPluginLoader loader, LukkitPluginFile file) throws InvalidPluginException {
        this.pluginFile = file;
        this.pluginLoader = loader;

        try {
            this.descriptor = new PluginDescriptionFile(this.pluginFile.getPluginYML());
        } catch (InvalidDescriptionException e) {
            throw new InvalidPluginException("The description provided was invalid or missing.");
        }

        this.name = this.descriptor.getName();
        this.logger = new PluginLogger(this);
        this.globals = LuaEnvironment.getNewGlobals(this);

        try {
            this.pluginMain = this.globals.load(new InputStreamReader(this.pluginFile.getResource(this.descriptor.getMain()), "UTF-8"), this.descriptor.getMain());
        } catch (UnsupportedEncodingException e) {
            throw new InvalidPluginException("File could not be loaded using UTF-8.", e.getCause());
        }
        this.dataFolder = new File(Main.instance.getDataFolder().getParentFile().getAbsolutePath() + File.separator + this.name);
        if (!this.dataFolder.exists()) //noinspection ResultOfMethodCallIgnored
            this.dataFolder.mkdir();

        this.pluginConfig = new File(this.dataFolder + File.separator + "config.yml");
        this.config = new YamlConfiguration();
        this.loadConfigWithChecks();

        this.globals.set("plugin", new PluginWrapper(this));
        this.globals.set("logger", new LoggerWrapper(this));
        this.globals.set("util", new UtilitiesWrapper(this));
        this.globals.set("bukkit", new BukkitWrapper(this));
        this.globals.set("config", new ConfigWrapper(this));
        this.globals.set("storage", new StorageWrapper(this));

        // Sets callbacks (if any) and loads the commands & events into memory.
        Optional<String> isValid = this.checkValidity();
        if (isValid.isPresent())
            throw new InvalidPluginException("An issue occurred when loading the plugin: \n" + isValid.get());

        try {
            this.pluginMain.call();
            this.onLoad();
        } catch (LukkitPluginException e) {
            e.printStackTrace();
            LuaEnvironment.addError(e);
        }
    }

    @Override
    public File getDataFolder() {
        return this.dataFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return this.descriptor;
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public InputStream getResource(String path) {
        return this.pluginFile.getResource(path);
    }

    @Override
    public void saveConfig() {
        if (this.config != null) {
            try {
                this.config.save(pluginConfig);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void saveDefaultConfig() {
        try {
            Files.copy(this.pluginFile.getDefaultConfig(), new File(this.dataFolder.getAbsolutePath() + File.separator + "config.yml").toPath());
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath.startsWith("/")) resourcePath = resourcePath.replaceFirst("/", "");

        String fileName = resourcePath.split("/")[resourcePath.split("/").length - 1];
        File resourceOutput = new File(this.dataFolder.getAbsolutePath() + File.separator + fileName);
        InputStream is = this.pluginFile.getResource(resourcePath);

        if (is != null) {
            if (!resourceOutput.exists() || replace) {
                try {
                    Files.copy(is, resourceOutput.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    this.logger.severe("There was an issue copying a resource to the data folder.");
                    e.printStackTrace();
                }
            } else {
                this.logger.info("Will not export resource " + resourcePath + " to " + this.dataFolder.getName() + " as it already exists and has not been marked to be replaced.");
            }
        } else {
            this.logger.warning("The resource requested doesn't exist. Unable to find " + resourcePath + " in " + this.pluginFile.getPath());
        }
    }

    @Override
    public void reloadConfig() {
        if (this.pluginConfig != null) {
            try {
                this.config.load(this.dataFolder);
            } catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
        }
    }

    @Override
    public PluginLoader getPluginLoader() {
        return this.pluginLoader;
    }

    @Override
    public Server getServer() {
        return Main.instance.getServer();
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void onEnable() {
        this.enabled = true;
        try {
            if (this.enableCB != null) this.enableCB.call(CoerceJavaToLua.coerce(this));
        } catch (LukkitPluginException e) {
            e.printStackTrace();
            LuaEnvironment.addError(e);
        }

        Main.events.forEach((s, e) -> this.getServer().getPluginManager().registerEvent(e, new Listener() {}, EventPriority.NORMAL, (listener, event) -> this.onEvent(event), this, false));
    }

    @Override
    public void onDisable() {
        this.enabled = false;
        try {
            if (this.disableCB != null) this.disableCB.call(CoerceJavaToLua.coerce(this));
        } catch (LukkitPluginException e) {
            e.printStackTrace();
            LuaEnvironment.addError(e);
        }
    }

    @Override
    public void onLoad() {
        try {
            if (this.loadCB != null) this.loadCB.call();
        } catch (LukkitPluginException e) {
            e.printStackTrace();
            LuaEnvironment.addError(e);
        }
    }

    @Override
    public boolean isNaggable() {
        return this.naggable;
    }

    @Override
    public void setNaggable(boolean isNaggable) {
        this.naggable = isNaggable;
    }

    @Override
    public EbeanServer getDatabase() {
        // Deprecated
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return Main.instance.getDefaultWorldGenerator(worldName, id);
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (commands.containsKey(command.getName())) {
            try {
                commands.get(command.getName()).invoke(new LuaValue[] {
                        CoerceJavaToLua.coerce(sender),
                        CoerceJavaToLua.coerce(command),
                        LuaValue.valueOf(label),
                        CoerceJavaToLua.coerce(args)
                });
            } catch (LukkitPluginException e) {
                e.printStackTrace();
                LuaEnvironment.addError(e);
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] strings) {
        // TODO
        return null;
    }

    public File getFile() {
        return new File(this.pluginFile.getPath());
    }

    public boolean isDevPlugin() {
        return this.pluginFile.isDevPlugin();
    }

    public void setLoadCB(LuaFunction cb) {
        this.loadCB = cb;
    }

    public void setEnableCB(LuaFunction cb) {
        this.enableCB = cb;
    }

    public void setDisableCB(LuaFunction cb) {
        this.disableCB = cb;
    }

    public void addCommand(String name, LuaFunction function) {
        this.commands.put(name, function);
    }

    public void registerEvent(Class<? extends Event> event, LuaFunction function) {
        if (this.getEventCallbacks(event.getSimpleName()) == null) {
            ArrayList<LuaFunction> list = new ArrayList<>();
            list.add(function);
            this.eventCallbacks.put(event.getSimpleName(), list);
        } else {
            this.eventCallbacks.get(event.getSimpleName()).add(function);
        }
    }

    public LukkitPluginFile getPluginFile() {
        return this.pluginFile;
    }

    private ArrayList<LuaFunction> getEventCallbacks(String simpleName) {
        return this.eventCallbacks.get(simpleName);
    }

    private void onEvent(Event e) {
        ArrayList<LuaFunction> callbacks = this.getEventCallbacks(e.getClass().getSimpleName());
        if (callbacks != null) callbacks.forEach((f) -> {
            try {
                f.call(CoerceJavaToLua.coerce(Class.forName(e.getClass().getName()).cast(e)));
            } catch (ClassNotFoundException ex) {
                this.logger.severe("Unable to cast event of type " + e.getEventName() + " to object. Event will not be handled by this plugin. I'd recommend that you send your plugin and the stacktrace to the developer on GitHub via an issue.");
                ex.printStackTrace();
            } catch (LukkitPluginException ex) {
                ex.printStackTrace();
                LuaEnvironment.addError(ex);
            }
        });
    }

    private void loadConfigWithChecks() {
        InputStream internalConfig = this.pluginFile.getDefaultConfig();
        if (!this.pluginConfig.exists() && internalConfig == null) {
            // No need to do anything, there is no config.
            this.config = null;
        } else if (!this.pluginConfig.exists()) {
            // There is no external config so we'll export one from the .lkt
            try {
                Files.createFile(this.pluginConfig.toPath());
                Files.copy(internalConfig, this.pluginConfig.toPath(), StandardCopyOption.REPLACE_EXISTING);
                this.loadConfig();
            } catch (IOException e) {
                this.logger.warning("Unable to export the internal config. We have a problem.");
                e.printStackTrace();
            }
        } else {
            // There is a config externally and one internally. External is fine, just load that.
            this.loadConfig();
        }
    }

    private void loadConfig() {
        File brokenConfig;

        try {
            this.config.load(this.pluginConfig);
            return;
        } catch (InvalidConfigurationException e) {
            brokenConfig = new File(this.dataFolder.getAbsolutePath() + File.separator + "config.broken.yml");
        } catch (IOException e) {
            this.logger.severe("There was an error creating the file to move the broken config to.");
            e.printStackTrace();
            return;
        }

        try {
            Files.copy(this.pluginConfig.toPath(), brokenConfig.toPath());
            Files.copy(this.pluginFile.getDefaultConfig(), this.pluginConfig.toPath());
            this.config.load(this.pluginConfig);
        } catch (IOException e) {
            this.logger.severe("There was an error copying either the broken config to its new file or the default config to the data folder.");
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            this.logger.severe("The internal config is invalid. If you are the plugin maintainer please verify it. If you believe this is a bug submit an issue on GitHub with your configuration.");
            e.printStackTrace();
        }

        this.logger.warning("The config at " + this.pluginConfig.getAbsolutePath() + " was invalid. It has been moved to config.broken.yml and the default config has been exported to config.yml.");
    }

    private Optional<String> checkValidity() {
        if (this.pluginMain == null) {
            return Optional.of("Unable to load the main Lua file. It may be missing from the plugin file or corrupted.");
        } else if (this.descriptor == null) {
            return Optional.of("Unable to load the plugin's description file. It may be missing from the plugin file or corrupted.");
        }

        return Optional.empty();
    }
}
