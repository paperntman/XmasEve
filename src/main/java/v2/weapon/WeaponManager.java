package v2.weapon;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import v2.Main;
import v2.weapon.list.Practice_LongSword;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WeaponManager {
    private static Set<iWeapon> weaponSet = new HashSet<>();
    public static iWeapon getWeaponAsId(String id){
        final List<iWeapon> weapon = weaponSet.stream().filter(iWeapon -> iWeapon.getId().equalsIgnoreCase(id)).toList();
        return weapon.isEmpty() ? null : weapon.get(0);
    }
    public static iWeapon getWeaponAsItem(ItemStack itemStack){
        final List<iWeapon> weapon = weaponSet.stream().filter(iWeapon -> isEqual(iWeapon.getItem(), itemStack)).toList();
        return weapon.isEmpty() ? null : weapon.get(0);
    }

    public static boolean isEqual(ItemStack i1, ItemStack i2){

        if(i1 == null && i2 == null) return true;
        if(i1 == null || i2 == null){
            return false;
        }

        boolean display = Objects.equals(i1.getItemMeta().getDisplayName(), i2.getItemMeta().getDisplayName());
        boolean lore = Objects.equals(i1.getItemMeta().getLore(), i2.getItemMeta().getLore());
        boolean type = i1.getType().equals(i2.getType());

        return display&&lore&&type;
    }

    public static Set<iWeapon> getWeaponSet() {
        return weaponSet;
    }

    public static void setup(){
        weaponSet.add(new Practice_LongSword());

        for (iWeapon iWeapon : weaponSet) {
            Bukkit.getPluginManager().registerEvents(iWeapon, Main.main);
        }
    }
}
