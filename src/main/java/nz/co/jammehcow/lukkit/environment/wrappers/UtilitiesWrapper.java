package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class UtilitiesWrapper extends LuaTable {
    private LukkitPlugin plugin;
    private static Set<Class<?>> classes;
    static {
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.bukkit"))));;

        classes = reflections.getSubTypesOf(Object.class);
    }

    public UtilitiesWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;

        set("getTableFromList", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                Object[] list;

                if (arg.checkuserdata() instanceof Collection) {
                    list = ((Collection<?>) arg.touserdata()).toArray();
                } else if (arg.touserdata() instanceof Stream) {
                    list = ((Stream<?>) arg.touserdata()).toArray();
                } else {
                    throw new LuaError("util.tableFromList(obj) was passed something other than an instance of Collection or Stream.");
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

        set("runAsync", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue function, LuaValue delay) {
                Thread thread = new Thread(() -> {
                    try {
                        if (delay != null) Thread.sleep(delay.checklong());
                        function.checkfunction().call();
                    } catch (InterruptedException ignored) {}
                });
                thread.start();
                return LuaValue.NIL;
            }
        });

        set("runDelayed", new TwoArgFunction() {
            // Runs the given function SYNCHRONOUSLY. Delay is in milliseconds.
            @Override
            public synchronized LuaValue call(LuaValue function, LuaValue time) {
                System.out.println("before");

                ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
                ScheduledFuture future = execService.schedule((Callable<LuaValue>) function::call, time.checklong(), TimeUnit.MILLISECONDS);

                while (true) {
                    if (future.isDone()) {
                        execService.shutdown();
                        notify();
                        return LuaValue.NIL;
                    }
                }
            }
        });

        set("castObject", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue object, LuaValue cast) {
                plugin.getLogger().warning("Don't rely on utils.castObject() for your plugin. It will be removed (most likely) before 2.0 and should only be used for Lukkit's development.");
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
