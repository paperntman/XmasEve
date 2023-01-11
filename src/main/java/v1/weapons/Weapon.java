package v1.weapons;

import org.bukkit.inventory.ItemStack;

public interface Weapon {
    enum weaponType{
        Sword, Shield, Rod, Bow
    }

    public weaponType getType();

    public ItemStack getItem();
    public String getId();



}
