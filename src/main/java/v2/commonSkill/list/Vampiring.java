package v2.commonSkill.list;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import v2.PlayerFileManager;
import v2.commonSkill.SkillManager;
import v2.commonSkill.iPassiveSkill;
import v2.TopenPlayer;

public class Vampiring implements iPassiveSkill {

    @Override
    public char getRank() {
        return 'D';
    }

    @Override
    public String getName() {
        return "흡혈";
    }

    @Override
    public String getId() {
        return "vampiring";
    }

    @Override
    public String getDescription() {
        return "입힌 피해의 33%를 회복합니다.";
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(! SkillManager.skillsUsing.contains(getId())) return;
        if (e.getDamager() instanceof Player player) {
            System.out.printf("%s hit %s, healed %f\n", player.getName(), e.getEntity().getType().name(), e.getDamage() / 3);
            final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
            if (topenPlayer.getIPassiveSkills().contains(getId())) {
                topenPlayer.heal(Math.min(
                        e.getFinalDamage() / 3 + player.getHealth(),
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()
                ));
            }
        }else if(e.getDamager() instanceof Projectile projectile){
            if (projectile.getShooter() instanceof Player player) {
                System.out.printf("%s shot %s, healed %f\n", player.getName(), e.getEntity().getType().name(), e.getDamage() / 3);
                final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
                if (topenPlayer.getIPassiveSkills().contains(getId())) {
                    topenPlayer.heal(Math.min(
                            e.getFinalDamage() / 3 + player.getHealth(),
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()
                    ));
                }
            }
        }
    }
}
