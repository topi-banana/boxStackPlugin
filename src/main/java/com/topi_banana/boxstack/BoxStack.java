package com.topi_banana.boxstack;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.logging.Logger;
import java.util.Random;

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
