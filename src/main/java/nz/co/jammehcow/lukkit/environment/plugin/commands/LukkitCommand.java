package nz.co.jammehcow.lukkit.environment.plugin.commands;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class LukkitCommand extends Command {

    private final LuaFunction function;
    private final LukkitPlugin plugin;
    private boolean registered = false;
    private boolean runAsync = false;
    private int minArgs = 0;
    private int maxArgs = -1;
    // TODO: Add options to use min/max args, set permission, set run async, and more helper functions

    public LukkitCommand(LukkitPlugin plugin, LuaFunction function, String name) {
        this(plugin, function, name, "", "", new String[0]);
    }

    public LukkitCommand(LukkitPlugin plugin, LuaFunction function, String name, String description) {
        this(plugin, function, name, description, "", new String[0]);
    }


    public LukkitCommand(LukkitPlugin plugin, LuaFunction function, String name, String description, String usage) {
        this(plugin, function, name, description, usage, new String[0]);
    }

    public LukkitCommand(LukkitPlugin plugin, LuaFunction function, String name, String description, String usage, String[] aliases) {
        super(name.toLowerCase(), description, usage, Arrays.asList(aliases));
        this.function = function;
        this.plugin = plugin;
    }

    private static Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    public void register() throws NoSuchFieldException, IllegalAccessException {
        if (getName() == null || getDescription() == null || registered)
            return;
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

        bukkitCommandMap.setAccessible(true);
        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        commandMap.register(plugin.getDescription().getName(), this);
        registered = true;
    }

    public void unregister() throws NoSuchFieldException, IllegalAccessException {
        Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
        SimpleCommandMap commandMap = (SimpleCommandMap) result;
        Object map = getPrivateField(commandMap, "knownCommands");
        HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
        knownCommands.remove(getName());
        for (String alias : getAliases()) {
            if (knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(this.getName())) {
                knownCommands.remove(alias);
            }
        }
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args) {
        try {
            function.invoke(new LuaValue[]{
                    new CommandEvent(sender, command, args)
            });
        } catch (LukkitPluginException e) {
            e.printStackTrace();
            LuaEnvironment.addError(e);
        }
        return true;
    }
}
