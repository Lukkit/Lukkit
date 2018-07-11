package nz.co.jammehcow.lukkit.environment.plugin.commands;

import nz.co.jammehcow.lukkit.Utilities;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.scheduler.BukkitScheduler;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LukkitCommand extends Command {

    private static final String ERROR_MISSING_ARGS = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " Missing args.";
    private static final String ERROR_TOO_MANY_ARGS = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " Too many args.";
    private static final String ERROR_INVALID_SENDER = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " You can not run this command.";
    private static final String ERROR_NO_PERMISSION = ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR!" +
            ChatColor.RED + " No permission.";

    private final LuaFunction function;
    private final LukkitPlugin plugin;
    private LuaFunction tabComleteFunction;
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
        if (!this.testPermissionSilent(sender)) {
            sender.sendMessage(ERROR_NO_PERMISSION);
            return true;
        }
        try {
            if (args.length > maxArgs && maxArgs >= 0) {
                sender.sendMessage(ERROR_TOO_MANY_ARGS);
            } else if (args.length < minArgs) {
                sender.sendMessage(ERROR_MISSING_ARGS);
            } else {
                if (runAsync) {
                    BukkitScheduler scheduler = Bukkit.getScheduler();
                    scheduler.runTaskAsynchronously(plugin, () -> function.invoke(new LuaValue[]{new CommandEvent(sender, command, args)}));
                } else {
                    function.invoke(new LuaValue[]{new CommandEvent(sender, command, args)});
                }
            }
        } catch (LukkitPluginException e) {
            e.printStackTrace();
            LuaEnvironment.addError(e);
        }
        return true;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public void setMaxArgs(int maxArgs) {
        this.maxArgs = maxArgs;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public boolean isRunAsync() {
        return runAsync;
    }

    public void setRunAsync(boolean runAsync) {
        this.runAsync = runAsync;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> def = super.tabComplete(sender, alias, args);

        if (tabComleteFunction != null) {
            LuaValue val = tabComleteFunction.invoke(
                    CoerceJavaToLua.coerce(sender),
                    CoerceJavaToLua.coerce(alias),
                    CoerceJavaToLua.coerce(args)
            ).arg1();
            if (val != LuaValue.NIL) {
                LuaTable tbl = val.checktable();
                Object o = Utilities.convertTable(tbl);
                if (o instanceof List)
                    return (List<String>) o;
            }
        }
        return def;
    }


    public void onTabComlete(LuaValue f) {
        tabComleteFunction = f.checkfunction();
    }
}
