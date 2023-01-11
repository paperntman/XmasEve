package v1.event.result;

import org.bukkit.event.player.PlayerInteractEvent;
import v1.XPlayer;
import v1.weapons.Weapon;
import v1.weapons.WeaponManager;
import v1.weapons.WeaponRodInterface;
import v1.skills.SkillManager;

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
