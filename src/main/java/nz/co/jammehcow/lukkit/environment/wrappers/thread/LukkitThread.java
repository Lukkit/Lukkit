package nz.co.jammehcow.lukkit.environment.wrappers.thread;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.luaj.vm2.LuaFunction;

public class LukkitThread {
    private Thread thread;

    LukkitThread(LuaFunction func, LukkitPlugin plugin, long delay) {
        this.thread = new Thread(
                null,
                () -> {
                    try {
                        // Negative delays are ignored, treated as no delay
                        if (delay > 0L) {
                            Thread.sleep(delay);
                        }
                        func.call();
                    } catch (InterruptedException ignored) {
                    }
                },
                plugin.getThreadPool().generateThreadName()
        );

        this.thread.start();
    }

    public boolean isAlive() {
        return this.thread.isAlive();
    }

    public String getName() {
        return this.thread.getName();
    }

    public synchronized void end() {
        this.thread.interrupt();
    }
}
