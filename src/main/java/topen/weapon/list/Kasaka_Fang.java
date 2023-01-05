package topen.weapon.list;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import topen.Main;
import topen.weapon.iWeapon;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Kasaka_Fang implements iWeapon {
    @Override
    public String getId() {
        return "Kasaka's-Fang";
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.GREEN + "카사카의 독니");
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 6, AttributeModifier.Operation.ADD_NUMBER));

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player attacker){
            if (attacker.getInventory().getItemInMainHand().equals(getItem())) {
                Random random = new Random();
                if (random.nextInt(100)<5) {
                    freeze.put(e.getEntity(), new temp(e.getEntity().getLocation().clone(), e.getEntity().getWorld().getFullTime()));
                    taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, () -> {
                        if(freeze.isEmpty()) stopTask();
                    }, 0, 1);
                }
            }
        }
    }

    private void stopTask(){
        Bukkit.getScheduler().cancelTask(taskId);
    }
    private int taskId = 0;
    Map<Entity, temp> freeze = new HashMap<>();

    class temp{
        Location location;
        long tick;

        public temp(Location location, long tick) {
            this.location = location;
            this.tick = tick;
        }
    }

}
