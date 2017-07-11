package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class UtilitiesWrapper extends LuaTable {
    private LukkitPlugin plugin;

    public UtilitiesWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;

        set("broadcast", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                plugin.getServer().broadcastMessage(arg.checkjstring());
                return LuaValue.NIL;
            }
        });

        set("getTableFromList", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                Object[] list;

                if (arg.checkuserdata() instanceof Collection) {
                    list = ((Collection<?>) arg.touserdata()).toArray();
                } else if (arg.touserdata() instanceof Stream) {
                    list = ((Stream<?>) arg.touserdata()).toArray();
                } else {
                    throw new LuaError("utils.tableFromList(obj) was passed something other than an instance of Collection or Stream.");
                }

                LuaTable t = new LuaTable();
                for (int i = 0; i < list.length; i++) {
                    t.set(LuaValue.valueOf(i + 1), ConfigWrapper.castObject(list[i]));
                }

                return t;
            }
        });

        // TODO: map to keyed table.

        set("getTableLength", new OneArgFunction() {
            // Useful when you have a table with set keys (like strings) and you want to get the size of it. Using # will return 0.
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaValue.valueOf(arg.checktable().keyCount());
            }
        });
        set("castObject", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue object, LuaValue cast) {
                List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
                classLoadersList.add(ClasspathHelper.contextClassLoader());
                classLoadersList.add(ClasspathHelper.staticClassLoader());

                Reflections reflections = new Reflections(new ConfigurationBuilder()
                        .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.bukkit"))));;

                Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

                Class<?> obj = null;

                for (Class<?> clazz : classes) {
                    if (Objects.equals(clazz.getSimpleName(), cast.tojstring())) {
                        System.out.println("Found the class to cast with");
                        obj = clazz;
                        break;
                    }
                }

                if (obj != null) {
                    try {
                        System.out.println("Casting");
                        return CoerceJavaToLua.coerce(Class.forName(obj.getName()).cast(object.touserdata()));
                    } catch (ClassNotFoundException e) {
                        System.out.println("Failed cast");
                        return LuaValue.NIL;
                    }
                }

                System.out.println("Not casting");
                return LuaValue.NIL;
            }
        });
    }
}
