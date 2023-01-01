package org.example.event.result;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponBowInterface;
import org.example.weapons.WeaponManager;

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
