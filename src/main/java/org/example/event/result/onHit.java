package org.example.event.result;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponManager;
import org.example.weapons.WeaponSwordInterface;

public class onHit {

    public void event(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player player){
            WeaponManager.getWeaponList().forEach(weaponInterface -> {
                if(weaponInterface.getType().equals(Weapon.weaponType.Sword))
                    if(weaponInterface.getItem().equals(player.getInventory().getItemInMainHand()))
                        ((WeaponSwordInterface) weaponInterface).hitEvent(e);
            });
        } else if (e.getDamager() instanceof Arrow arrow) {
            if(onBow.entityList.containsKey(arrow)){
                onBow.entityList.get(arrow).hitEvent(e);
            }
        }
    }
}
