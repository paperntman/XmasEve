package v1.event.result;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import v1.weapons.Weapon;
import v1.weapons.WeaponManager;
import v1.weapons.WeaponSwordInterface;

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
