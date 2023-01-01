package org.example;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.command.getitem;
import org.example.event.EventListener;
import org.example.weapons.WeaponManager;

import java.io.File;

public class Main extends JavaPlugin {

    public static File DataFolder;
    public static Main main;
    @Override
    public void onEnable() {
        DataFolder = getDataFolder();
        getServer().getLogger().info("Xmas-Plugin Loaded!");
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        WeaponManager.setup();
        getCommand("getitem").setExecutor(new getitem());
        main = this;
    }

}
