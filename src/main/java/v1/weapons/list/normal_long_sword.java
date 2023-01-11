package v1.weapons.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import v1.weapons.WeaponSwordInterface;

import java.util.Random;

public class normal_long_sword implements WeaponSwordInterface {


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

    @Override
    public String getId() {
        return "normal_long_sword";
    }

    @Override
    public weaponType getType() {
        return weaponType.Sword;
    }

    @Override
    public void hitEvent(EntityDamageByEntityEvent e) {
        Random random = new Random();
        if (random.nextInt(100) == 1) {
            if(e.getDamager() instanceof Player){
                ((Player) e.getDamager()).getInventory().getItemInMainHand().setType(Material.AIR);
            }
        }

    }
}
