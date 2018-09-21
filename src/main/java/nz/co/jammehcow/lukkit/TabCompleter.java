package nz.co.jammehcow.lukkit;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private static String[] subCommands = {"help", "plugins", "dev", "run"};
    private static String[] devSubCommands = {"reload", "unload", "pack", "unpack", "last-error", "errors", "new-plugin"};

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (command.getName().startsWith("lukkit"))
            if (args.length == 1) {
                return getFilteredCompletions(args[0], subCommands);
            } else if (args.length == 2) {
                // Set the String "cmd" to the first arg and remove the arg from the "args" array.
                String cmd = args[0];
                // Get a new array with the first arg omitted
                args = (String[]) ArrayUtils.remove(args, 0);

                if (cmd.equalsIgnoreCase("dev")) {
                    List<String> plugins = new ArrayList<>();
                    // Iterate over the plugins and add them to the map by lower-cased name
                    Main.instance.iteratePlugins(p -> plugins.add(p.getName()));

                    String[] pluginArr = plugins.toArray(new String[plugins.size()]);

                    if (args.length == 1) {
                        return getFilteredCompletions(args[0], devSubCommands);
                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("reload") ||
                                args[0].equalsIgnoreCase("unload") ||
                                args[0].equalsIgnoreCase("pack") ||
                                args[0].equalsIgnoreCase("unpack")) {
                            return getFilteredCompletions(args[1], pluginArr);
                        }
                    } else if (args[0].equalsIgnoreCase("errors")) {
                        Optional<Stream<Exception>> errors = LuaEnvironment.getErrors();

                        errors.ifPresent(exceptionStream -> exceptionStream.forEach(new Consumer<Exception>() {
                            int count = 0;

                            @Override
                            public void accept(Exception e) {
                                tabComplete.add(String.valueOf(count));
                                count++;
                            }
                        }));
                    }
                }
            }
        return tabComplete;
    }

    private List<String> getFilteredCompletions(String arg, String[] subCommands) {
        ArrayList<String> returnCompletions = new ArrayList<>();

        if (!arg.equals("")) {
            for (String s : subCommands) {
                if (s.startsWith(arg)) returnCompletions.add(s);
            }

            return returnCompletions;
        } else return Arrays.asList(subCommands);
    }
}
