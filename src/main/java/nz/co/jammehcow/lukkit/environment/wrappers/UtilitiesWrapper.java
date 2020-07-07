package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class UtilitiesWrapper extends LuaTable {

    private LukkitPlugin plugin;
    private ScheduledExecutorService runDelayedThreadPool;

    public UtilitiesWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;
        this.runDelayedThreadPool = Executors.newScheduledThreadPool(1);

        set("getTableFromList", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                Object[] list;

                if (arg.checkuserdata() instanceof Collection) {
                    list = ((Collection<?>) arg.touserdata()).toArray();
                } else if (arg.touserdata() instanceof Stream) {
                    list = ((Stream<?>) arg.touserdata()).toArray();
                } else {
                    throw new LukkitPluginException("util.tableFromList(obj) was passed something other than an instance of Collection or Stream.");
                }

                LuaTable t = new LuaTable();
                for (int i = 0; i < list.length; i++) {
                    t.set(LuaValue.valueOf(i + 1), CoerceJavaToLua.coerce(list[i]));
                }

                return t;
            }
        });

        set("getTableFromArray", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                Object[] list = (Object[]) arg.touserdata();

                LuaTable t = new LuaTable();
                for (int i = 0; i < list.length; i++) {
                    t.set(LuaValue.valueOf(i + 1), CoerceJavaToLua.coerce(list[i]));
                }

                return t;
            }
        });

        set("getTableFromMap", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                Map<?, ?> map;

                if (arg.checkuserdata() instanceof Map) {
                    map = (Map<?, ?>) arg.touserdata();
                } else {
                    throw new LukkitPluginException("util.tableFromMap(obj) was passed something other than a implementation of Map.");
                }

                LuaTable t = new LuaTable();
                map.forEach((k, v) -> t.set(CoerceJavaToLua.coerce(k), CoerceJavaToLua.coerce(v)));

                return t;
            }
        });

        set("getTableLength", new OneArgFunction() {
            // Useful when you have a table with set keys (like strings) and you want to get the size of it. Using # will return 0.
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaValue.valueOf(arg.checktable().keyCount());
            }
        });

        set("runAsync", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue function, LuaValue delay) {
                Thread thread = new Thread(() -> {
                    try {
                        if (delay != LuaValue.NIL) Thread.sleep(delay.checklong());
                        function.checkfunction().call();
                    } catch (InterruptedException ignored) {
                    }
                });

                thread.start();
                return LuaValue.NIL;
            }
        });

        set("runDelayed", new TwoArgFunction() {
            // Delay is in milliseconds.
            @Override
            public LuaValue call(LuaValue function, LuaValue time) {
                ScheduledFuture<LuaValue> future = runDelayedThreadPool.schedule((Callable<LuaValue>) function::call, time.checklong(), TimeUnit.MILLISECONDS);

                try {
                    // Blocking call, we don't care about the value
                    future.get();
                } catch (Exception e) {
                    plugin.getLogger().warning("The thread spawned by runDelayed was terminated or threw an exception");
                    LuaEnvironment.addError(e);
                    e.printStackTrace();
                }

                return LuaValue.NIL;
            }
        });

        set("getBukkitRunnable", new OneArgFunction() {
            @Override
            public LuaValue call( LuaValue function ) {
                LuaFunction func = function.checkfunction();
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        func.call();
                    };
                };
                return CoerceJavaToLua.coerce(task);
            };
        });

        set("getClass", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue path) {
                try {
                    return CoerceJavaToLua.coerce(Class.forName(path.checkjstring()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return NIL;
            }
        });


        set("getSkullMeta", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue item) {
                if (!item.isnil() && !(item.checkuserdata() instanceof ItemStack)) {
                    throw new LukkitPluginException("bukkit.getSkullMeta was passed something other than an ItemStack.");
                }

                return CoerceJavaToLua.coerce(new SkullWrapper((item.isnil()) ? null : (ItemStack) item.touserdata()));
            }
        });

        set("getBannerMeta", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue item) {
                if (!item.isnil() && !(item.checkuserdata() instanceof ItemStack)) {
                    throw new LukkitPluginException("bukkit.getBannerMeta was passed something other than an ItemStack.");
                }

                return CoerceJavaToLua.coerce(new BannerWrapper((item.isnil()) ? null : (ItemStack) item.touserdata()));
            }
        });

        set("parseItemStack", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue item) {
                if (!item.isnil() && !(item.checkuserdata() instanceof ItemStack)) {
                    throw new LukkitPluginException("parseItemStack was given something other than an ItemStack");
                }

                return CoerceJavaToLua.coerce(new ItemStackWrapper((ItemStack) item.touserdata()));
            }
        });

        // Temporary method, fixed in v3
        set("cast", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue userdata, LuaValue clazz) {
                String className = clazz.checkjstring();
                Object obj = userdata.checkuserdata();

                try {
                    Class<?> caster = Class.forName(className);
                    return userdataOf(caster.cast(obj));
                } catch (ClassNotFoundException e) {
                    plugin.getLogger().warning("Could not find class " + className);
                } catch (ClassCastException e) {
                    plugin.getLogger().warning("Provided userdata cannot be casted to " + className);
                } catch (LinkageError e) {
                    plugin.getLogger().warning("There was an unknown issue casting the object to " + className);
                    e.printStackTrace();
                }

                return NIL;
            }
        });

        set("instanceof", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue obj, LuaValue classToCheck) {
                Class<?> clazz = (Class) classToCheck.checkuserdata();
                return CoerceJavaToLua.coerce(clazz.isInstance(obj.checkuserdata()));
            };
        });    
    }

    public void close() {
        runDelayedThreadPool.shutdown();
    }

    @Override
    public String typename() {
        return ObjectType.WRAPPER.name;
    }

    @Override
    public int type() {
        return ObjectType.WRAPPER.type;
    }
}
