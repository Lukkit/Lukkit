package nz.co.jammehcow.lukkit.environment.plugin;

import com.avaje.ebean.EbeanServer;
import nz.co.jammehcow.lukkit.Main;
import nz.co.jammehcow.lukkit.Utilities;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.plugin.commands.LukkitCommand;
import nz.co.jammehcow.lukkit.environment.wrappers.ConfigWrapper;
import nz.co.jammehcow.lukkit.environment.wrappers.LoggerWrapper;
import nz.co.jammehcow.lukkit.environment.wrappers.PluginWrapper;
import nz.co.jammehcow.lukkit.environment.wrappers.UtilitiesWrapper;
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
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.*;
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
    private UtilitiesWrapper utilitiesWrapper;
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
        Globals globals = LuaEnvironment.getNewGlobals(this);

        this.pluginMain = globals.load(new InputStreamReader(this.pluginFile.getResource(this.descriptor.getMain()), StandardCharsets.UTF_8), this.descriptor.getMain());
        this.dataFolder = new File(Main.instance.getDataFolder().getParentFile().getAbsolutePath() + File.separator + this.name);
        if (!this.dataFolder.exists()) //noinspection ResultOfMethodCallIgnored
            this.dataFolder.mkdir();

        this.pluginConfig = new File(this.dataFolder + File.separator + "config.yml");
        this.config = new YamlConfiguration();
        this.loadConfigWithChecks();

        setupPluginGlobals(globals);

        // Sets callbacks (if any) and loads the commands & events into memory.
        Optional<String> isValid = this.checkPluginValidity();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveDefaultConfig() {
        try {
            Files.copy(this.pluginFile.getDefaultConfig(), new File(this.dataFolder.getAbsolutePath() + File.separator + "config.yml").toPath());
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

        eventListeners.forEach((event, list) ->
                list.forEach(function ->
                        this.getServer().getPluginManager().registerEvent(event, new Listener() {
                        }, EventPriority.NORMAL, (l, e) -> function.call(CoerceJavaToLua.coerce(e)), this, false)
                ));
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
        unregisterAllCommands();
        utilitiesWrapper.close();
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
            Listener listener = new Listener() {
            };

            this.getServer().getPluginManager().registerEvent(event, listener, EventPriority.NORMAL, (l, e) -> function.call(CoerceJavaToLua.coerce(e)), this, false);
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
            this.logger.severe("There was an error copying either the broken config to its new file or the default config to the data folder.");
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            this.logger.severe("The internal config is invalid. If you are the plugin maintainer please verify it. If you believe this is a bug submit an issue on GitHub with your configuration.");
            e.printStackTrace();
        }

        this.logger.warning("The config at " + this.pluginConfig.getAbsolutePath() + " was invalid. It has been moved to config.broken.yml and the default config has been exported to config.yml.");
    }

    /**
     * Set up convenience methods on Lua globals
     *
     * @param globals globals to set up properties on
     */
    private void setupPluginGlobals(Globals globals) {
        globals.set("plugin", new PluginWrapper(this));
        globals.set("logger", new LoggerWrapper(this));
        // use a member as its internal threadpool needs to be shutdown upon disabling the plugin
        utilitiesWrapper = new UtilitiesWrapper(this);
        globals.set("util", utilitiesWrapper);
        globals.set("config", new ConfigWrapper(this));

        OneArgFunction oldRequire = (OneArgFunction) globals.get("require");

        globals.set("require", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                String path = luaValue.checkjstring();
                if (!path.endsWith(".lua"))
                    path += ".lua";

                // Replace all but last dot
                path = path.replaceAll("\\.(?=[^.]*\\.)", "/");

                InputStream resource = pluginFile.getResource(path);

                if (resource == null) {
                    return oldRequire.call(luaValue);
                }

                try {
                    return globals.load(new InputStreamReader(resource, "UTF-8"), luaValue.checkjstring()).call();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return NIL;

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
            public LuaValue invoke(Varargs vargs) {
                String classPath = vargs.checkjstring(1);
                LuaValue args = vargs.optvalue(2, LuaValue.NIL);

                // Parse classpath shorthands
                if (classPath.startsWith("$"))
                    classPath = "org.bukkit" + classPath.substring(1);
                else if (classPath.startsWith("#"))
                    classPath = "nz.co.jammehcow.lukkit.environment" + classPath.substring(1);

                // Validate the classpath isn't just bullshit
                if (!Utilities.isClassPathValid(classPath)) {
                    LukkitPluginException classPathException = new LukkitPluginException("An invalid classpath \"" +
                            classPath + "\" was provided to the \"newInstance\" method");
                    LuaEnvironment.addError(classPathException);

                    throw classPathException;
                }

                LuaString classPathValue = LuaValue.valueOf(classPath);
                LuaValue newInstanceMethod = globals.get("luajava").get("newInstance");

                switch (args.type()) {
                    case LuaValue.TNIL:
                        return newInstanceMethod.invoke(classPathValue).checkvalue(1);
                    case LuaValue.TTABLE:
                        LuaTable argTable = args.checktable();
                        LuaValue[] varargArray = new LuaValue[argTable.length() + 1];
                        varargArray[0] = classPathValue;

                        for (int iKey = 1; iKey < varargArray.length; iKey++) {
                            varargArray[iKey] = argTable.get(iKey);
                        }

                        return newInstanceMethod.invoke(varargArray).checkvalue(1);
                    default:
                        LukkitPluginException exception = new LukkitPluginException("Second argument of newInstance " +
                                "must be of type table, not " + args.typename());
                        LuaEnvironment.addError(exception);
                        throw exception;
                }
            }
        });
    }

    private Optional<String> checkPluginValidity() {
        if (this.pluginMain == null) {
            return Optional.of("Unable to load the main Lua file. It may be missing from the plugin file or corrupted.");
        } else if (this.descriptor == null) {
            return Optional.of("Unable to load the plugin's description file. It may be missing from the plugin file or corrupted.");
        }

        return Optional.empty();
    }

    public void registerCommand(LukkitCommand command) {
        commands.add(command);
        try {
            command.register();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void unregisterCommand(LukkitCommand command) {
        commands.remove(command);
        try {
            command.unregister();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void unregisterAllCommands() {
        // Create new array to get rid of concurrent modification
        List<LukkitCommand> cmds = new ArrayList<>();
        cmds.addAll(commands);
        cmds.forEach(this::unregisterCommand);
    }
}
