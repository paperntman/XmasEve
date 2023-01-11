package v2.weapon.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import v2.weapon.iWeapon;

import java.util.Random;

public class Practice_LongSword implements iWeapon {

    @Override
    public String getId() {
        return "practice_longsword";
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.GREEN + "연습용 장검");
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player player){
            if (player.getInventory().getItemInMainHand().equals(getItem())) {
                Random random = new Random();
                System.out.println("good");
                if (random.nextInt(100)==0) {
                    player.getInventory().getItemInMainHand().setType(Material.AIR);
                }
            }
        }
    }
}
