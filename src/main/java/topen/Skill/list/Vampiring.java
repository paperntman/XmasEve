package topen.Skill.list;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import topen.PlayerManager;
import topen.Skill.SkillManager;
import topen.Skill.iPassiveSkill;
import topen.TopenPlayer;

import java.util.Arrays;

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
        for (Player onlinePlayer : e.getEntity().getServer().getOnlinePlayers()) {
            System.out.println(Arrays.toString(PlayerManager.getPlayer(onlinePlayer.getUniqueId()).iPassiveSkills.toArray())); //TODO delete
        }
        if(! SkillManager.skillsUsing.contains(getId())) return;
        if (e.getDamager() instanceof Player player) {
            final TopenPlayer topenPlayer = PlayerManager.getPlayer(player.getUniqueId());
            if (topenPlayer.iPassiveSkills.contains(getId())) {
                player.setHealth(Math.min(
                        e.getDamage() / 3 + player.getHealth(),
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()
                ));
            }
        }
    }
}
