package v1.weapons.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import v1.weapons.WeaponBowInterface;

import java.util.Random;

public class normal_bow implements WeaponBowInterface {
    @Override
    public weaponType getType() {
        return weaponType.Bow;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(Material.BOW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.GREEN + "연습용 활");
        itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getId() {
        return "normal_bow";
    }

    @Override
    public void useEvent(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player){
            final Player player = (Player) e.getEntity();
            Random random = new Random();
            if(random.nextInt(100) == 0)
                player.getInventory().getItemInMainHand().setType(Material.AIR);
        }
    }

    @Override
    public void hitEvent(EntityDamageByEntityEvent e) {
        Random random = new Random();
        if(random.nextInt(100) < 3)
            e.setCancelled(true);
    }
}
