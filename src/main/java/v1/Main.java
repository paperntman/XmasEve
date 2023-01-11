package v1;

import org.bukkit.plugin.java.JavaPlugin;
import v1.command.getitem;
import v1.event.EventListener;
import v1.weapons.WeaponManager;

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
