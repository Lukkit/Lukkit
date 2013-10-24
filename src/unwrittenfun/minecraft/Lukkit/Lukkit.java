package unwrittenfun.minecraft.lukkit;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class Lukkit extends JavaPlugin {
    public static Lukkit instance;

    @Override
    public void onEnable() {
        instance = this;
    }
}
