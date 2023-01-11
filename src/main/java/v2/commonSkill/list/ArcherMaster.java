package v2.commonSkill.list;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import v2.PlayerFileManager;
import v2.commonSkill.SkillManager;
import v2.commonSkill.iPassiveSkill;
import v2.TopenPlayer;

public class ArcherMaster implements iPassiveSkill {
    @Override
    public char getRank() {
        return 'B';
    }

    @Override
    public String getName() {
        return "궁수의 극한";
    }

    @Override
    public String getId() {
        return "Archer_Master";
    }

    @Override
    public String getDescription() {
        return "쏘는 활이 기본적으로 불화살이 되며\n화살의 속도가 2배 상승합니다.";
    }

    @EventHandler
    public void onPlayerShootEvent(EntityShootBowEvent e){
        if(! SkillManager.skillsUsing.contains(getId()) || ! (e.getEntity() instanceof Player player)) return;
        final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
        if (topenPlayer.getIPassiveSkills().contains(getId()) && e.getProjectile() instanceof Arrow arrow) {
            arrow.setFireTicks(100);
            arrow.setVelocity(arrow.getVelocity().multiply(2));
            arrow.setDamage(arrow.getDamage() * 2);
        }
    }
}
