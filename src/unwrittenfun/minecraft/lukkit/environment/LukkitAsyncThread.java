package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class LukkitAsyncThread extends Thread {
    private Thread t;
    private String threadName;
    private LuaFunction luafunc;

    public LukkitAsyncThread(String name, LuaFunction _luafunc) {
        threadName = name;
        luafunc = _luafunc;
    }

    @Override
    public synchronized void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    @Override
    public void run() {
        luafunc.call(CoerceJavaToLua.coerce(t));
    }
}
