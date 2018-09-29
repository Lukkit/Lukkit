package nz.co.jammehcow.lukkit.environment.plugin;

import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.Utilities;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.plugin.commands.LukkitCommand;
import nz.co.jammehcow.lukkit.environment.wrappers.ConfigWrapper;
import nz.co.jammehcow.lukkit.environment.wrappers.LoggerWrapper;
import nz.co.jammehcow.lukkit.environment.wrappers.PluginWrapper;
import nz.co.jammehcow.lukkit.environment.wrappers.UtilitiesWrapper;
import nz.co.jammehcow.lukkit.environment.wrappers.thread.LukkitThreadPool;
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
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginLogger;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
    private final LukkitPluginLoader pluginLoader;
    private final PluginDescriptionFile descriptor;
    private final File dataFolder;
    private final Logger logger;
    private final List<LukkitCommand> commands = new ArrayList<>();
    private final HashMap<Class<? extends Event>, ArrayList<LuaFunction>> eventListeners = new HashMap<>();
    private final LukkitThreadPool threadPool;
    private LuaFunction loadCB;
    private LuaFunction enableCB;
    private LuaFunction disableCB;
    private File pluginConfig;
    private FileConfiguration config;
    private boolean enabled = false;
    private boolean naggable = true;

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
            e.printStackTrace();
            throw new InvalidPluginException("The description provided was invalid or missing.");
        }

        this.name = this.descriptor.getName();
        this.logger = new PluginLogger(this);

        this.dataFolder = new File(Main.instance.getDataFolder().getParentFile().getAbsolutePath()
                + File.separator + this.name);
        if (!this.dataFolder.exists()) //noinspection ResultOfMethodCallIgnored
            this.dataFolder.mkdir();

        this.pluginConfig = new File(this.dataFolder + File.separator + "config.yml");
        this.config = new YamlConfiguration();
        this.loadConfigWithChecks();

        Globals globals = this.initializeGlobals();
        this.pluginMain = globals.load(
                new InputStreamReader(this.pluginFile.getResource(this.descriptor.getMain()), StandardCharsets.UTF_8),
                this.descriptor.getMain()
        );

        // Sets callbacks (if any) and loads the commands & events into memory.
        Optional<String> isValid = this.checkPluginValidity();
        if (isValid.isPresent())
            throw new InvalidPluginException("An issue occurred when loading the plugin: \n" + isValid.get());

        this.threadPool = new LukkitThreadPool(this);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveDefaultConfig() {
        try {
            Files.copy(
                    this.pluginFile.getDefaultConfig(),
                    new File(this.dataFolder.getAbsolutePath() + File.separator + "config.yml").toPath()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                this.logger.info("Will not export resource " + resourcePath + " to " + this.dataFolder.getName()
                        + " as it already exists and has not been marked to be replaced.");
            }
        } else {
            this.logger.warning("The resource requested doesn't exist. Unable to find " + resourcePath
                    + " in " + this.pluginFile.getPath());
        }
    }

    @Override
    public void reloadConfig() {
        if (this.pluginConfig != null) {
            try {
                this.config.load(this.dataFolder);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
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

        this.eventListeners.forEach((event, list) ->
                list.forEach(function ->
                        this.getServer().getPluginManager().registerEvent(event, new Listener() {
                        }, EventPriority.NORMAL, (l, e) -> function.call(CoerceJavaToLua.coerce(e)), this, false)
                ));
    }

    @Override
    public void onDisable() {
        this.enabled = false;

        this.threadPool.shutdown();

        try {
            if (this.disableCB != null) this.disableCB.call(CoerceJavaToLua.coerce(this));
        } catch (LukkitPluginException e) {
            e.printStackTrace();
            LuaEnvironment.addError(e);
        }
        unregisterAllCommands();
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
        return true;
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

    public Listener registerEvent(Class<? extends Event> event, LuaFunction function) {
        getEventListeners(event).add(function);
        if (this.enabled) {
            Listener listener = new Listener() {};

            this.getServer().getPluginManager().registerEvent(
                    event, listener, EventPriority.NORMAL,
                    (l, e) -> function.call(org.luaj.vm2.lib.jse.CoerceJavaToLua.coerce(e)),
                    this, false
            );
        }
        return null;
    }

    public LukkitPluginFile getPluginFile() {
        return this.pluginFile;
    }

    private ArrayList<LuaFunction> getEventListeners(Class<? extends Event> event) {
        this.eventListeners.computeIfAbsent(event, k -> new ArrayList<>());
        return this.eventListeners.get(event);
    }

    private Globals initializeGlobals() {
        Globals globals = LuaEnvironment.getNewGlobals(this);

        globals.set("plugin", new PluginWrapper(this));
        globals.set("logger", new LoggerWrapper(this));
        globals.set("util", new UtilitiesWrapper(this));
        globals.set("config", new ConfigWrapper(this));

        globals.set("require", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                String path = luaValue.checkjstring();
                if (!path.endsWith(".lua"))
                    path += ".lua";

                // Replace all but last dot
                path = path.replaceAll("\\.(?=[^.]*\\.)", "/");

                return globals.load(
                        new InputStreamReader(pluginFile.getResource(path),
                                StandardCharsets.UTF_8
                        ), luaValue.checkjstring()
                ).call();
            }
        });

        globals.set("import", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                try {
                    String path = luaValue.checkjstring();
                    if (path.startsWith("$"))
                        path = "org.bukkit" + path.substring(1);
                    if (path.startsWith("#"))
                        path = "nz.co.jammehcow.lukkit.environment" + path.substring(1);
                    return CoerceJavaToLua.coerce(Class.forName(path));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return NIL;
            }
        });
        globals.set("newInstance", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue cls, LuaValue args) {
                String classPath = cls.checkjstring();
                try {
                    if (classPath.startsWith("$"))
                        classPath = "org.bukkit" + classPath.substring(1);
                    if (classPath.startsWith("#"))
                        classPath = "nz.co.jammehcow.lukkit.environment" + classPath.substring(1);
                    if (args.isnil()) {
                        return CoerceJavaToLua.coerce(Class.forName(classPath).newInstance());
                    } else if (args.istable()) {
                        List<Object> argList = (List<Object>) Utilities.convertTable(args.checktable());
                        List<Class<?>> typesList = new ArrayList<>();

                        argList.forEach(o -> typesList.add(o.getClass()));

                        return CoerceJavaToLua.coerce(Class.forName(classPath).getDeclaredConstructor(
                                typesList.toArray(new Class<?>[0]))
                                .newInstance(argList.toArray(new Object[0])));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return NIL;
            }
        });

        return globals;
    }

    // TODO: combine both config methods into one.

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
            this.logger.severe("There was an error copying either the broken config to its new file or the default " +
                    "config to the data folder.");
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            this.logger.severe("The internal config is invalid. If you are the plugin maintainer please verify it. " +
                    "If you believe this is a bug submit an issue on GitHub with your configuration.");
            e.printStackTrace();
        }

        this.logger.warning("The config at " + this.pluginConfig.getAbsolutePath() + " was invalid. " +
                "It has been moved to config.broken.yml and the default config has been exported to config.yml.");
    }

    private Optional<String> checkPluginValidity() {
        if (this.pluginMain == null) {
            return Optional.of("Unable to load the main Lua file. " +
                    "It may be missing from the plugin file or corrupted.");
        } else if (this.descriptor == null) {
            return Optional.of("Unable to load the plugin's description file. " +
                    "It may be missing from the plugin file or corrupted.");
        }

        return Optional.empty();
    }

    public void registerCommand(LukkitCommand command) {
        this.commands.add(command);
        try {
            command.register();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void unregisterCommand(LukkitCommand command) {
        this.commands.remove(command);
        try {
            command.unregister();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void unregisterAllCommands() {
        // Create new array to get rid of concurrent modification
        List<LukkitCommand> cmds = new ArrayList<>(this.commands);
        cmds.forEach(this::unregisterCommand);
    }

    public LukkitThreadPool getThreadPool() {
        return this.threadPool;
    }
}
