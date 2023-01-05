package topen;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;
import topen.commonSkill.SkillBookImporter;
import topen.commonSkill.SkillManager;
import topen.commonSkill.ActiveSkillManager;
import topen.cmd.WorldTeleport;
import topen.cmd.getBook;
import topen.cmd.getItem;
import topen.commonSkill.InventoryClick;
import topen.commonSkill.InventoryCmd;
import topen.cmd.smartKey;
import topen.weapon.WeaponManager;

import java.util.Random;

public class Main extends JavaPlugin{

    public static Main main;
    @Override
    public void onEnable() {
        getDataFolder().mkdir();
        Bukkit.getLogger().info("[Xmas-Eve] Enabled!");
        Bukkit.getPluginManager().registerEvents(new SkillBookImporter(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new ActiveSkillManager(), this);
        Bukkit.getPluginManager().registerEvents(new DamageManager(), this);
        Bukkit.getPluginManager().registerEvents(new DontSpawnKill(), this);
        main = this;
        getCommand("getbook").setTabCompleter(new getBook());
        getCommand("getbook").setExecutor(new getBook());
        getCommand("getitem").setTabCompleter(new getItem());
        getCommand("getitem").setExecutor(new getItem());
        getCommand("inventory").setExecutor(new InventoryCmd());
        getCommand("worldteleport").setTabCompleter(new WorldTeleport());
        getCommand("worldteleport").setExecutor(new WorldTeleport());
        getCommand("smartkey").setExecutor(new smartKey());
        SkillManager.setup();
        WeaponManager.setup();

        worldCreate("Realm_of_Death");
        worldCreate("world_sixROcket");
    }

    public void worldCreate(String name){
       if(Bukkit.getWorld(name) != null) return;
        WorldCreator wc = new WorldCreator(name);
        wc.generator(new VoidGenerator()); // let's create a class for generator of void
        wc.generateStructures(false);
        wc.type(WorldType.FLAT);
        wc.createWorld();

    }

    class VoidGenerator extends ChunkGenerator {

        public void generateSurface(WorldInfo info, Random random, int x, int z, ChunkData data) {
            for (int y = info.getMinHeight(); y < info.getMaxHeight(); y++) {
                data.setBlock(x, y, z, Material.AIR);
            }
        }

        public boolean shouldGenerateNoise() {
            return false;
        }

        public boolean shouldGenerateBedrock() {
            return false;
        }

        public boolean shouldGenerateCaves() {
            return false;
        }
    }
}
