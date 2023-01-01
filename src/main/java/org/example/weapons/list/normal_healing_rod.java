package org.example.weapons.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.weapons.WeaponRodInterface;

import java.util.List;

public class normal_healing_rod implements WeaponRodInterface {
    @Override
    public weaponType getType() {
        return weaponType.Rod;
    }

    @Override
    public ItemStack getItem() {

        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.GREEN + "연습용 힐링 요술봉");
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getId() {
        return "normal_healing_rod";
    }

    @Override
    public void interactEvent(PlayerInteractEvent e) {
        if(e.getPlayer().hasCooldown(Material.STICK)) return;
        e.getPlayer().setCooldown(Material.STICK, 20);
        final List<Entity> nearbyEntities = new java.util.ArrayList<>(e.getPlayer().getNearbyEntities(5, 5, 5).stream()
                .filter(entity1 -> entity1 instanceof Player).toList());
        if(nearbyEntities.size()==0) nearbyEntities.add(e.getPlayer());
        final Player entity = (Player) nearbyEntities.stream()
                .sorted((o1, o2) -> {
                    final double o1Health = ((LivingEntity) o1).getHealth();
                    final double o1MaxHealth = ((LivingEntity) o1).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                    final double o2Health = ((LivingEntity) o2).getHealth();
                    final double o2MaxHealth = ((LivingEntity) o2).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                    double returnValue = o1Health / o1MaxHealth - o2Health / o2MaxHealth;

                    if (returnValue < 0) return -1;
                    if (returnValue > 0) return 1;
                    return 0;

                }).toList().get(0);

        entity.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, entity.getLocation().add(0, 1, 0), 50, 0.5, 1, 0.5);
        entity.setHealth(Math.min(entity.getHealth() + 2, entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        entity.setFoodLevel(entity.getFoodLevel()+1);
    }
}
