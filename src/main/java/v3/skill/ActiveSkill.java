package v3.skill;

import org.bukkit.entity.Player;

public interface ActiveSkill extends Skill {
    public void onUse(Player player);
}
