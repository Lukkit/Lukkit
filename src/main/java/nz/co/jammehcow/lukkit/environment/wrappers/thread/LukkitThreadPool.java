package nz.co.jammehcow.lukkit.environment.wrappers.thread;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaFunction;

import java.util.ArrayList;

public class LukkitThreadPool {
    private static ArrayList<LukkitThreadPool> pluginPools = new ArrayList<>();
    private final LukkitPlugin plugin;
    private int currentThreadID = 0;
    private ArrayList<LukkitThread> pool = new ArrayList<>();
    public LukkitThreadPool(LukkitPlugin plugin) {
        this.plugin = plugin;
        pluginPools.add(this);
    }

    public static synchronized void shutdownAll() {
        // Safety from the Lukkit plugin disable method
        pluginPools.forEach(LukkitThreadPool::shutdown);
    }

    public LukkitThread createThread(LuaFunction func, long delay) {
        LukkitThread thread = new LukkitThread(func, this.plugin, delay);
        this.pool.add(thread);
        return thread;
    }

    public void shutdown() {
        pool.forEach(LukkitThread::end);
    }

    String generateThreadName() {
        // For example, a plugin called "Testing-Plugin" will make a thread called "TestingPlugin-0"
        return this.plugin.getName().replace("-", "") + "-" + this.currentThreadID++;
    }
}
