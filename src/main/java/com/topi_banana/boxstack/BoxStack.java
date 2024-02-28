package com.topi_banana.boxstack;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class BoxStack extends JavaPlugin {
    public static Logger LOGGER;
    @Override
    public void onEnable() {
        LOGGER = getLogger();
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
