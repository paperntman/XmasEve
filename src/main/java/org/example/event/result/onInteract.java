package org.example.event.result;

import org.bukkit.event.player.PlayerInteractEvent;
import org.example.XPlayer;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponManager;
import org.example.weapons.WeaponRodInterface;
import org.example.skills.SkillManager;

public class onInteract {
    public void event(PlayerInteractEvent e) {
        WeaponManager.getWeaponList().forEach(weaponInterface -> {
            if(weaponInterface.getType().equals(Weapon.weaponType.Rod))
                if(weaponInterface.getItem().equals(e.getPlayer().getInventory().getItemInMainHand()))
                    ((WeaponRodInterface) weaponInterface).interactEvent(e);
        });
        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().get(0).equalsIgnoreCase("우클릭을 통해 학습할 수 있습니다.")){
            XPlayer player = new XPlayer(e.getPlayer());
            player.addSkill(SkillManager.getSkillAsName());
        }
    }
}
