package dev.artex.lukkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onLoad() {
        // Stub
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Lukkit enabled! https://git.io/fhH9F");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Lukkit disabled! https://git.io/fhH9F");
    }
}
