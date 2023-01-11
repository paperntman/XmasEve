package v2.weapon;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface iWeapon extends Listener {
    public String getId();
    public ItemStack getItem();
}
