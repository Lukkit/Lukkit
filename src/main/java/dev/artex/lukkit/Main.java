package dev.artex.lukkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onLoad() {
        // Stub
    }

    @Override
    public void onEnable() {
        this.getLogger().info(Constants.MAIN_ENABLE);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(Constants.MAIN_DISABLE);
    }
}
