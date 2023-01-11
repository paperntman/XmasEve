package v1.event.result;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import v1.weapons.Weapon;
import v1.weapons.WeaponBowInterface;
import v1.weapons.WeaponManager;

import java.util.*;

public class onBow {

    public static Map<Entity, WeaponBowInterface> entityList = new HashMap<>();
    public void event(EntityShootBowEvent e){
        if(e.getEntity() instanceof Player){
            WeaponManager.getWeaponList().forEach(weaponInterface -> {
                if(weaponInterface.getType().equals(Weapon.weaponType.Bow)){
                    final WeaponBowInterface bowInterface = (WeaponBowInterface) weaponInterface;
                    entityList.put(e.getProjectile(), bowInterface);
                    bowInterface.useEvent(e);
                }
            });
        }
    }
}
